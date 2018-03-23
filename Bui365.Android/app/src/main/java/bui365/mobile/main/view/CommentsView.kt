package bui365.mobile.main.view

import bui365.mobile.main.BaseView
import bui365.mobile.main.presenter.CommentsPresenter


interface CommentsView : BaseView<CommentsPresenter> {
    fun loadComments()
}
