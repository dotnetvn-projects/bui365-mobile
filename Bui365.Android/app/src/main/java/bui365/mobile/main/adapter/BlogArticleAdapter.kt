package bui365.mobile.main.adapter


import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import bui365.mobile.main.R
import bui365.mobile.main.model.pojo.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.util.*

class BlogArticleAdapter(private val mContext: Context, private val mArticles: ArrayList<Article>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_ITEM = 0
    private val VIEW_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        if (viewType == VIEW_ITEM) {
            val v = LayoutInflater.from(mContext).inflate(R.layout.item_handbook_article, parent, false)
            vh = ItemViewHolder(v)
        } else {
            val v = LayoutInflater.from(mContext).inflate(R.layout.item_progress, parent, false)
            vh = ProgressHolder(v)
        }
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val article = mArticles[position]
            Glide.with(mContext).load(R.drawable.da_lat)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()).placeholder(R.drawable.icon_mobile))
                    .into(holder.imgAvatar)
            holder.txtName.text = article.title
            holder.txtTime.text = article.updatedDate
            Glide.with(mContext).load(article.image)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                            holder.progressCircle.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            holder.progressCircle.visibility = View.GONE
                            return false
                        }
                    })
                    .into(holder.imgPicture)
            holder.txtDescription.text = article.description
        } else {
            (holder as ProgressHolder).progressBar.isIndeterminate = true
        }
    }

    override fun getItemCount(): Int {
        return mArticles.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mArticles[position] != null) VIEW_ITEM else VIEW_LOADING
    }

    private inner class ItemViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {
        internal var imgAvatar: ImageView
        internal var imgPicture: ImageView
        internal var progressCircle: ProgressBar
        internal var txtLike: TextView
        internal var txtComment: TextView
        internal var txtShare: TextView
        internal var txtName: TextView
        internal var txtTime: TextView
        internal var txtDescription: TextView

        init {
            imgAvatar = v.findViewById(R.id.imgAvatar)
            imgPicture = v.findViewById(R.id.imgPicture)
            progressCircle = v.findViewById(R.id.progressCircle)
            txtLike = v.findViewById(R.id.txtLike)
            txtComment = v.findViewById(R.id.txtComment)
            txtShare = v.findViewById(R.id.txtShare)
            txtName = v.findViewById(R.id.txtName)
            txtTime = v.findViewById(R.id.txtTime)
            txtDescription = v.findViewById(R.id.txtDescription)
        }
    }

    private inner class ProgressHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {
        internal var progressBar: ProgressBar

        init {
            progressBar = v.findViewById(R.id.progressBar)
        }
    }
}
