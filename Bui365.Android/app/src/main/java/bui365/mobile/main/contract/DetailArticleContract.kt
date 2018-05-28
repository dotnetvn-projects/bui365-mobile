package bui365.mobile.main.contract

import bui365.mobile.main.BasePresenter
import bui365.mobile.main.BaseView
import bui365.mobile.main.model.pojo.Article

interface DetailArticleContract {
    interface View : BaseView<Presenter> {
        fun showResult(article: Article)

    }

    interface Presenter : BasePresenter {
        fun loadDetailArticle(articleId: String)

    }
}