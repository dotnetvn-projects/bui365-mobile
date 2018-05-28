package bui365.mobile.main.contract

import bui365.mobile.main.BasePresenter
import bui365.mobile.main.BaseView
import bui365.mobile.main.model.pojo.Article

interface SearchArticleContract {
    interface View : BaseView<Presenter> {
        fun showArticles(articles: List<Article>)
    }

    interface Presenter : BasePresenter {
        fun getArticles(search: String)
    }
}