package bui365.mobile.main.business


import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.interactor.HandbookDetailArticleInteractor
import bui365.mobile.main.interactor.impl.HandbookDetailArticleInteractorImpl
import bui365.mobile.main.presenter.HandbookDetailArticlePresenter
import bui365.mobile.main.view.HandbookDetailArticleView

import com.google.common.base.Preconditions.checkNotNull

class HandbookDetailArticleBusiness(detailArticleView: HandbookDetailArticleView) :
        HandbookDetailArticlePresenter, AsyncTaskListener<Any> {
    private val mDetailArticleView: HandbookDetailArticleView =
            checkNotNull(detailArticleView, "Detail Article View cannot null")
    private val mDetailArticleInteractor: HandbookDetailArticleInteractor

    init {
        this.mDetailArticleInteractor = HandbookDetailArticleInteractorImpl()
        mDetailArticleView.presenter = this
    }

    override fun start() {

    }

    override fun loadTask(forceUpdate: Boolean, index: Int) {

    }

    /**
     * @param articleId pass Article Id to call Detail Request
     */
    override fun loadDetailArticle(articleId: String) {
        mDetailArticleInteractor.getTask(articleId, this)
    }

    override fun onTaskPreExecute() {
        mDetailArticleView.showLoading()
    }

    override fun onTaskComplete(result: Any) {
        mDetailArticleView.hideLoading()
        if (result != "") {
            mDetailArticleView.hideError()
            mDetailArticleView.showResult(result)
        } else {
            mDetailArticleView.showError()
        }
    }
}
