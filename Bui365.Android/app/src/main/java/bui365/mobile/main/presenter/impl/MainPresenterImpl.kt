package bui365.mobile.main.presenter.impl


import bui365.mobile.main.business.MainBusiness
import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.presenter.MainActivityPresenter
import bui365.mobile.main.request.SampleRequest
import bui365.mobile.main.view.MainActivityView
import com.google.common.base.Preconditions.checkNotNull

class MainPresenterImpl(mainActivityView: MainActivityView) : MainActivityPresenter {
    private val mainActivityView: MainActivityView = checkNotNull(mainActivityView, "mainActivityView cannot be null")
    private val mainBusiness: MainBusiness = MainBusiness()
    private var articles: ArrayList<Article> = ArrayList()
    private var isFirstLoad = true

    init {
        this.mainActivityView.presenter = this
    }

    override fun start() {
        loadTask(false)
    }

    override fun loadTask(forceUpdate: Boolean) {
        loadTask(forceUpdate || isFirstLoad, true)
        isFirstLoad = false
    }

    /**
     * @param forceUpdate   Pass in true to refresh data
     * @param showLoadingUi Pass in true to display loading icon
     */
    private fun loadTask(forceUpdate: Boolean, showLoadingUi: Boolean) {
        if (showLoadingUi) {
            mainActivityView.showLoading()
        }
        if (forceUpdate) {
            //refresh method
        }

        //Call random image api request
        SampleRequest(object : AsyncTaskListener<String> {
            override fun onTaskPreExecute() {
                mainActivityView.showLoading()
            }

            override fun onTaskComplete(result: Any) {
                mainActivityView.hideLoading()
                if (!mainBusiness.isEmptyArticle(result)) {
                    mainActivityView.hideError()
                    articles = mainBusiness.handleData(result)
                    mainActivityView.showResult(articles)
                } else {
                    mainActivityView.showError()
                }
            }

        }).execute()
    }

    override fun openTaskDetails(id: Int) {
        mainActivityView.showTaskDetailUi(id)
    }
}
