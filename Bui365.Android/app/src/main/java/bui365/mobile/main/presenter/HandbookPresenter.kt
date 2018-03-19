package bui365.mobile.main.presenter


import bui365.mobile.main.BasePresenter

interface HandbookPresenter : BasePresenter {

    fun loadTask(forceUpdate: Boolean, index: Int)
}
