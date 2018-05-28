package bui365.mobile.main.contract

import bui365.mobile.main.BasePresenter
import bui365.mobile.main.BaseView
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.model.pojo.FacebookPOJO
import com.facebook.share.model.ShareLinkContent

interface BlogContract {
    interface View : BaseView<Presenter> {
        fun showResult(articles: List<Article>, loadEnd: Boolean)

        fun showFacebookSdk(facebookPOJO: FacebookPOJO)

        fun showShareArticle(content: ShareLinkContent)

        fun showComment(url: String)

        fun showDetailArticle(id: String)
    }

    interface Presenter : BasePresenter {

        fun loadTask(forceUpdate: Boolean, start: Int)

        fun loadFacebookSdk(url: String)

        fun shareArticle(url: String)

        fun loadComment(url: String)

        fun loadDetailArticle(id: String)

    }
}