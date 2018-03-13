package bui365.mobile.main.interactor.impl

import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.interactor.HandbookDetailArticleInteractor
import bui365.mobile.main.request.HandbookDetailArticleRequest


class HandbookDetailArticleInteractorImpl : HandbookDetailArticleInteractor {
    override fun getTask(id: String, listener: AsyncTaskListener<*>) {
        HandbookDetailArticleRequest(object : AsyncTaskListener<Any> {

            override fun onTaskPreExecute() {
                listener.onTaskPreExecute()
            }

            override fun onTaskComplete(result: Any) {
                listener.onTaskComplete(result)
            }
        }, id).execute()
    }
}
