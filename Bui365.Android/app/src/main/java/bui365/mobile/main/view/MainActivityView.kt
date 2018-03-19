package bui365.mobile.main.view

import bui365.mobile.main.BaseView
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.presenter.MainActivityPresenter


interface MainActivityView : BaseView<MainActivityPresenter> {
    fun showTaskDetailUi(id: Int)

    fun showResult(articles: List<Article>)
}
