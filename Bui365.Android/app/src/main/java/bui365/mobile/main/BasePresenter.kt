package bui365.mobile.main


interface BasePresenter {

    fun start()

    /**
     *
     * @param forceUpdate
     * @param index
     */
    fun loadTask(forceUpdate: Boolean, index: Int)
}
