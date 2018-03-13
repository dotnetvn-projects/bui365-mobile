package bui365.mobile.main.impl


interface AsyncTaskListener<T> {
    fun onTaskPreExecute()

    fun onTaskComplete(result: Any)
}