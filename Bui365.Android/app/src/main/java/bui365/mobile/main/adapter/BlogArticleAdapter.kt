package bui365.mobile.main.adapter


import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bui365.mobile.main.R
import bui365.mobile.main.databinding.ItemBlogArticleBinding
import bui365.mobile.main.impl.HandbookArticleItemListener
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.model.pojo.EmptyArticle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_blog_article.view.*
import kotlinx.android.synthetic.main.item_progress.view.*
import java.util.*

class BlogArticleAdapter(private val context: Context, private val articles: ArrayList<Article>, private val articleItemListener: HandbookArticleItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_ITEM = 0
        private const val VIEW_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val vh: RecyclerView.ViewHolder
        vh = if (viewType == VIEW_ITEM) {
            val binding: ItemBlogArticleBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_blog_article, parent, false)
            ItemHolder(binding)
        } else {
            val v = LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false)
            ProgressHolder(v)
        }
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {
            holder.bindData(articles[position])
        } else {
            (holder as ProgressHolder).bindData()
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (articles[position] !is EmptyArticle) VIEW_ITEM else VIEW_LOADING
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ItemHolder(private val binding: ItemBlogArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(article: Article) {
            binding.article = article
            Glide.with(context).load(R.drawable.da_lat)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()).placeholder(R.drawable.icon_mobile))
                    .into(itemView.imgAvatar)
            Glide.with(context).load(article.image)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                            itemView.progressCircle.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            itemView.progressCircle.visibility = View.GONE
                            return false
                        }
                    })
                    .into(itemView.imgPicture)
            binding.executePendingBindings()
            binding.listener = articleItemListener
        }

    }

    inner class ProgressHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData() {
            itemView.progressBar.isIndeterminate = true
        }
    }
}