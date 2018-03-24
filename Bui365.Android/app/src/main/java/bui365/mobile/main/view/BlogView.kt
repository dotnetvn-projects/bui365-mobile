package bui365.mobile.main.view


import bui365.mobile.main.BaseView
import bui365.mobile.main.presenter.BlogPresenter
import com.facebook.share.model.ShareLinkContent

interface BlogView : BaseView<BlogPresenter> {
    fun showResult(result: Any)

    fun showShareArticle(content: ShareLinkContent)

    fun showComment(url: String)

    fun showDetailArticle(id: String)
}
