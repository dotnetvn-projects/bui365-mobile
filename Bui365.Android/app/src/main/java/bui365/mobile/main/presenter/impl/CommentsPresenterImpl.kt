package bui365.mobile.main.presenter.impl

import bui365.mobile.main.presenter.CommentsPresenter
import bui365.mobile.main.view.CommentsView
import com.google.common.base.Preconditions.checkNotNull

class CommentsPresenterImpl(commentsView: CommentsView) : CommentsPresenter {
    private val mCommentsView: CommentsView = checkNotNull(commentsView, "Comments View cannot be null")

    init {
        mCommentsView.presenter = this
    }

    override fun start() {
        mCommentsView.showLoading()
        mCommentsView.loadComments()
    }

}