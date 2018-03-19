package bui365.mobile.main.view


import bui365.mobile.main.BaseView
import bui365.mobile.main.presenter.HandbookPresenter

interface HandbookView : BaseView<HandbookPresenter>{
    fun showResult(result: Any)
}
