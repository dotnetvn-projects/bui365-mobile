package bui365.mobile.main.contract

import bui365.mobile.main.BasePresenter
import bui365.mobile.main.BaseView

interface DetailArticleContract {
    interface View : BaseView<Presenter> {
        fun showResult(result: Any)

    }

    interface Presenter : BasePresenter {
        fun loadDetailArticle(articleId: String)

    }
}