package bui365.mobile.main


interface BaseView<T> {

    fun showResult(result: Any)

    fun showError()

    fun hideError()

    fun showEmptyText()

    fun hideEmptyText()

    fun showLoading()

    fun hideLoading()

    var presenter: T
}
