package bui365.mobile.main.interactor.impl


import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.interactor.MainActivityInteractor
import bui365.mobile.main.request.SampleRequest

class MainActivityInteractorImpl : MainActivityInteractor {

    override fun getTask(listener: AsyncTaskListener<*>) {
        SampleRequest(object : AsyncTaskListener<String> {
            override fun onTaskPreExecute() {
                listener.onTaskPreExecute()
            }

            override fun onTaskComplete(result: Any) {
                listener.onTaskComplete(result)
            }

        }).execute()
    }
}
