package bui365.mobile.main.view


import bui365.mobile.main.BaseView
import bui365.mobile.main.presenter.HandbookDetailArticlePresenter

interface HandbookDetailArticleView : BaseView<HandbookDetailArticlePresenter>{
    fun showResult(result: Any)

}
