package bui365.mobile.main.presenter


import bui365.mobile.main.BasePresenter

interface BlogPresenter : BasePresenter {

    fun loadTask(forceUpdate: Boolean, start: Int)

    fun loadFacebookSdk(url: String)

    fun shareArticle(url: String)

    fun loadComment(url: String)

    fun loadDetailArticle(id: String)

}
