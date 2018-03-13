package bui365.mobile.main.interactor.impl

import android.util.Log

import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.interactor.HandbookInteractor
import bui365.mobile.main.request.HandbookArticleRequest


class HandbookInteractorImpl : HandbookInteractor {

    override fun getTask(start: Int, listener: AsyncTaskListener<*>) {
        HandbookArticleRequest(object : AsyncTaskListener<String> {
            override fun onTaskPreExecute() {
                listener.onTaskPreExecute()
            }

            override fun onTaskComplete(result: Any) {
                listener.onTaskComplete(result)
            }

        }, start).execute()
    }
}
