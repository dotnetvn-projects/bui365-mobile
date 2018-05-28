package bui365.mobile.main.contract

import bui365.mobile.main.BasePresenter
import bui365.mobile.main.BaseView
import bui365.mobile.main.model.pojo.Article

interface MainContract {
    interface View : BaseView<Presenter> {
        fun showTaskDetailUi(id: Int)

        fun showResult(articles: List<Article>)
    }

    interface Presenter : BasePresenter {
        fun openTaskDetails(id: Int)

        fun loadTask(forceUpdate: Boolean)
    }
}