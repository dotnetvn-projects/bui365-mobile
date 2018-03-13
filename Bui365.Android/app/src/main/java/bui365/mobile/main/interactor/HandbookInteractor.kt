package bui365.mobile.main.interactor


import bui365.mobile.main.impl.AsyncTaskListener

interface HandbookInteractor {
    fun getTask(start: Int, listener: AsyncTaskListener<*>)
}
