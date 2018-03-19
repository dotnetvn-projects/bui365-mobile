package bui365.mobile.main


interface BaseView<T> {

    fun showError()

    fun hideError()

    fun showLoading()

    fun hideLoading()

    var presenter: T
}
