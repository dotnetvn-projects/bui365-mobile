package bui365.mobile.main.presenter

import bui365.mobile.main.contract.CommentsContract
import com.google.common.base.Preconditions.checkNotNull

class CommentsPresenterImpl(commentsView: CommentsContract.View) : CommentsContract.Presenter {
    private val commentsView: CommentsContract.View = checkNotNull(commentsView, "Comments View cannot be null")

    init {
        this.commentsView.presenter = this
    }

    override fun subscribe() {
        commentsView.showLoading()
        commentsView.loadComments()
    }

    override fun unsubscribe() {
    }
}