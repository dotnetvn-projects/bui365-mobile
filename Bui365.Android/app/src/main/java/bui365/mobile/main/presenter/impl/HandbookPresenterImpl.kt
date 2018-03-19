package bui365.mobile.main.presenter.impl


import bui365.mobile.main.business.HandbookBusiness
import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.presenter.HandbookPresenter
import bui365.mobile.main.request.HandbookArticleRequest
import bui365.mobile.main.view.HandbookView
import com.google.common.base.Preconditions.checkNotNull

class HandbookPresenterImpl(handbookView: HandbookView) : HandbookPresenter {
    private val mHandbookView: HandbookView = checkNotNull(handbookView, "handbookView cannot be null")
    private val mHandbookBusiness: HandbookBusiness = HandbookBusiness()

    private var mFirstLoad = true

    init {
        mHandbookView.presenter = this
    }

    override fun start() {
        loadTask(false, 0)
    }

    override fun loadTask(forceUpdate: Boolean, index: Int) {
        loadTask(forceUpdate || mFirstLoad, true, index)
        mFirstLoad = false
    }

    /**
     * @param forceUpdate   Pass in true to refresh data
     * @param showLoadingUi Pass in true to display loading icon
     * @param index         Pass index to load more item
     */
    private fun loadTask(forceUpdate: Boolean, showLoadingUi: Boolean, index: Int) {
        if (showLoadingUi) {
            mHandbookView.showLoading()
        }
        if (forceUpdate) {
            //refresh method
        }

        HandbookArticleRequest(object : AsyncTaskListener<String> {
            override fun onTaskPreExecute() {
                mHandbookView.showLoading()
            }

            override fun onTaskComplete(result: Any) {
                mHandbookView.hideLoading()
                if (mHandbookBusiness.handleData(result)) {
                    mHandbookView.hideError()
                    mHandbookView.showResult(result)
                } else {
                    mHandbookView.showError()
                }
            }

        }, index).execute()

    }
}
