package bui365.mobile.main.view


import bui365.mobile.main.BaseView
import bui365.mobile.main.presenter.BlogPresenter

interface BlogView : BaseView<BlogPresenter>{
    fun showResult(result: Any)

}
