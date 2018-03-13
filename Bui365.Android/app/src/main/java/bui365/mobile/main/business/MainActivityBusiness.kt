package bui365.mobile.main.business


import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.interactor.MainActivityInteractor
import bui365.mobile.main.interactor.impl.MainActivityInteractorImpl
import bui365.mobile.main.presenter.MainActivityPresenter
import bui365.mobile.main.view.MainActivityView
import com.google.common.base.Preconditions.checkNotNull

class MainActivityBusiness(val mainActivityView: MainActivityView) : MainActivityPresenter, AsyncTaskListener<Any> {
    private val mMainView: MainActivityView = checkNotNull(mainActivityView, "mainView cannot be null")
    private val mainActivityInteractor: MainActivityInteractor

    private var mFirstLoad = true

    init {
        mainActivityInteractor = MainActivityInteractorImpl()
        mMainView.presenter = this
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
     */
    private fun loadTask(forceUpdate: Boolean, showLoadingUi: Boolean, index: Int) {
        if (showLoadingUi) {
            mMainView.showLoading()
        }
        if (forceUpdate) {
            //refresh method
        }
        mainActivityInteractor.getTask(this)
    }

    override fun openTaskDetails(id: Int) {
        mMainView.showTaskDetailUi(id)
    }

    override fun onTaskPreExecute() {
        mMainView.showLoading()
    }

    override fun onTaskComplete(result: Any) {
        mMainView.hideLoading()
        if (result != "") {
            mMainView.hideError()
            mMainView.showResult(result)
        } else {
            mMainView.showError()
        }
    }
}
