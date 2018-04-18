package bui365.mobile.main.presenter.impl


import bui365.mobile.main.business.HandbookDetailArticleBusiness
import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.presenter.HandbookDetailArticlePresenter
import bui365.mobile.main.request.HandbookDetailArticleRequest
import bui365.mobile.main.view.HandbookDetailArticleView
import com.google.common.base.Preconditions.checkNotNull

class HandbookDetailArticlePresenterImpl(detailArticleView: HandbookDetailArticleView) :
        HandbookDetailArticlePresenter {
    private val detailArticleView: HandbookDetailArticleView =
            checkNotNull(detailArticleView, "Detail Article View cannot null")
    private val detailArticleBusiness: HandbookDetailArticleBusiness = HandbookDetailArticleBusiness()

    init {
        this.detailArticleView.presenter = this
    }

    override fun start() {

    }

    /**
     * @param articleId pass Article Id to call Detail Request
     */
    override fun loadDetailArticle(articleId: String) {
        HandbookDetailArticleRequest(object : AsyncTaskListener<Any> {

            override fun onTaskPreExecute() {
                detailArticleView.showLoading()
            }

            override fun onTaskComplete(result: Any) {
                detailArticleView.hideLoading()
                if (detailArticleBusiness.handleData(result)) {
                    detailArticleView.hideError()
                    detailArticleView.showResult(result)
                } else {
                    detailArticleView.showError()
                }
            }
        }, articleId).execute()
    }
}
