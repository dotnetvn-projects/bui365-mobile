package bui365.mobile.main.presenter.impl


import bui365.mobile.main.business.HandbookDetailArticleBusiness
import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.presenter.HandbookDetailArticlePresenter
import bui365.mobile.main.request.HandbookDetailArticleRequest
import bui365.mobile.main.view.HandbookDetailArticleView
import com.google.common.base.Preconditions.checkNotNull

class HandbookDetailArticlePresenterImpl(detailArticleView: HandbookDetailArticleView) :
        HandbookDetailArticlePresenter {
    private val mDetailArticleView: HandbookDetailArticleView =
            checkNotNull(detailArticleView, "Detail Article View cannot null")
    private val mDetailArticleBusiness: HandbookDetailArticleBusiness = HandbookDetailArticleBusiness()

    init {
        mDetailArticleView.presenter = this
    }

    override fun start() {

    }

    /**
     * @param articleId pass Article Id to call Detail Request
     */
    override fun loadDetailArticle(articleId: String) {
        HandbookDetailArticleRequest(object : AsyncTaskListener<Any> {

            override fun onTaskPreExecute() {
                mDetailArticleView.showLoading()
            }

            override fun onTaskComplete(result: Any) {
                mDetailArticleView.hideLoading()
                if (mDetailArticleBusiness.handleData(result)) {
                    mDetailArticleView.hideError()
                    mDetailArticleView.showResult(result)
                } else {
                    mDetailArticleView.showError()
                }
            }
        }, articleId).execute()
    }
}
