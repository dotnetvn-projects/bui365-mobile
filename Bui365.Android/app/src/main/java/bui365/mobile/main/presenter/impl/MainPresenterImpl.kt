package bui365.mobile.main.presenter.impl


import bui365.mobile.main.business.MainBusiness
import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.presenter.MainActivityPresenter
import bui365.mobile.main.request.SampleRequest
import bui365.mobile.main.view.MainActivityView
import com.google.common.base.Preconditions.checkNotNull

class MainPresenterImpl(mainActivityView: MainActivityView) : MainActivityPresenter {
    private val mMainView: MainActivityView = checkNotNull(mainActivityView, "mainView cannot be null")
    private val mMainBusiness: MainBusiness = MainBusiness()
    private var articles: ArrayList<Article> = ArrayList()
    private var mFirstLoad = true

    init {
        mMainView.presenter = this
    }

    override fun start() {
        loadTask(false)
    }

    override fun loadTask(forceUpdate: Boolean) {
        loadTask(forceUpdate || mFirstLoad, true)
        mFirstLoad = false
    }

    /**
     * @param forceUpdate   Pass in true to refresh data
     * @param showLoadingUi Pass in true to display loading icon
     */
    private fun loadTask(forceUpdate: Boolean, showLoadingUi: Boolean) {
        if (showLoadingUi) {
            mMainView.showLoading()
        }
        if (forceUpdate) {
            //refresh method
        }

        //Call random image api request
        SampleRequest(object : AsyncTaskListener<String> {
            override fun onTaskPreExecute() {
                mMainView.showLoading()
            }

            override fun onTaskComplete(result: Any) {
                mMainView.hideLoading()
                if (!mMainBusiness.isEmptyArticle(result)) {
                    mMainView.hideError()
                    articles = mMainBusiness.handleData(result)
                    mMainView.showResult(articles)
                } else {
                    mMainView.showError()
                }
            }

        }).execute()
    }

    override fun openTaskDetails(id: Int) {
        mMainView.showTaskDetailUi(id)
    }
}
