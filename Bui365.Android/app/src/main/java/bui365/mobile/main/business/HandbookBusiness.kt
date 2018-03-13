package bui365.mobile.main.business


import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.interactor.HandbookInteractor
import bui365.mobile.main.interactor.impl.HandbookInteractorImpl
import bui365.mobile.main.presenter.HandbookPresenter
import bui365.mobile.main.view.HandbookView

import com.google.common.base.Preconditions.checkNotNull

class HandbookBusiness(handbookView: HandbookView) : HandbookPresenter, AsyncTaskListener<Any> {
    private val mHandbookView: HandbookView = checkNotNull(handbookView, "handbookView cannot be null")
    private val handbookInteractor: HandbookInteractor

    private var mFirstLoad = true

    init {
        this.handbookInteractor = HandbookInteractorImpl()
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
        handbookInteractor.getTask(index, this)
    }

    override fun onTaskPreExecute() {
        mHandbookView.showLoading()
    }

    override fun onTaskComplete(result: Any) {
        mHandbookView.hideLoading()
        if (result != "") {
            mHandbookView.hideError()
            mHandbookView.showResult(result)
        } else {
            mHandbookView.showError()
        }
    }
}
