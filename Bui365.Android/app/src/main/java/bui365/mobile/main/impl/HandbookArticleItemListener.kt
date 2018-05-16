package bui365.mobile.main.impl

import bui365.mobile.main.model.pojo.Article


interface HandbookArticleItemListener {
    fun onImageClick(article: Article)

    fun onCommentClick(article: Article)

    fun onShareClick(article: Article)
}