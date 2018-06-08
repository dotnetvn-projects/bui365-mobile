package bui365.mobile.main.contract

import bui365.mobile.main.BasePresenter
import bui365.mobile.main.BaseView
import bui365.mobile.main.model.pojo.Article

interface HandbookContract {
    interface View : BaseView<Presenter> {
        fun showHandbookArticle(articles: List<Article>, loadEnd: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getHandbookArticles(forceUpdate: Boolean, start: Int)
    }
}