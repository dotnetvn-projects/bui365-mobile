package bui365.mobile.main.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import bui365.mobile.main.R
import bui365.mobile.main.databinding.ItemProgressBinding
import bui365.mobile.main.databinding.ItemSearchArticleListBinding
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.model.pojo.EmptyArticle
import bui365.mobile.main.util.Constant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_progress.view.*
import kotlinx.android.synthetic.main.item_search_article.view.*

class SearchArticleAdapter(private val context: Context, private val articles: ArrayList<Article>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val vh: RecyclerView.ViewHolder
        vh = if (viewType == Constant.VIEW_ITEM) {
            val binding: ItemSearchArticleListBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_search_article_list, parent, false)
            ItemHolder(binding)
        } else {
            val binding: ItemProgressBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_progress, parent, false)
            ProgressHolder(binding)
        }

        return vh
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (articles[position] !is EmptyArticle) Constant.VIEW_ITEM else Constant.VIEW_LOADING
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {
            holder.bindData(articles[position])
        } else {
            (holder as ProgressHolder).bindData()
        }
    }

    inner class ItemHolder(private val binding: ItemSearchArticleListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(article: Article) {
            binding.article = article
            val spacing = context.resources.getDimensionPixelSize(R.dimen.activity_sub_margin)
            Glide.with(context).load(article.image)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(spacing)))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                    }).into(itemView.imgAvatar)
            binding.executePendingBindings()
        }
    }

    inner class ProgressHolder(binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData() {
            itemView.progressBar.isIndeterminate = true
        }
    }
}