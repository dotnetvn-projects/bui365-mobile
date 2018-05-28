package bui365.mobile.main.contract

import bui365.mobile.main.BasePresenter
import bui365.mobile.main.BaseView

interface CommentsContract {
    interface View : BaseView<Presenter> {
        fun loadComments()
    }

    interface Presenter : BasePresenter
}