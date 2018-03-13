package bui365.mobile.main.interactor


import bui365.mobile.main.impl.AsyncTaskListener

interface MainActivityInteractor {
    fun getTask(listener: AsyncTaskListener<*>)
}
