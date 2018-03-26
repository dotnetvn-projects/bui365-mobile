package bui365.mobile.main.view


import bui365.mobile.main.BaseView
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.model.pojo.FacebookPOJO
import bui365.mobile.main.presenter.BlogPresenter
import com.facebook.share.model.ShareLinkContent

interface BlogView : BaseView<BlogPresenter> {
    fun showResult(articles: List<Article>, loadEnd:Boolean)

    fun showFacebookSdk(facebookPOJO: FacebookPOJO)

    fun showShareArticle(content: ShareLinkContent)

    fun showComment(url: String)

    fun showDetailArticle(id: String)
}
