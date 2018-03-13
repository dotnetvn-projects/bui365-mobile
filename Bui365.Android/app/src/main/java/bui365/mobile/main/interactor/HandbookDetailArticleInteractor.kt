package bui365.mobile.main.interactor


import bui365.mobile.main.impl.AsyncTaskListener

interface HandbookDetailArticleInteractor {
    fun getTask(id: String, listener: AsyncTaskListener<*>)
}
