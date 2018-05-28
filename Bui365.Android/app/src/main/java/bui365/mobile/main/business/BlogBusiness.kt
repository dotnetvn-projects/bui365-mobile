package bui365.mobile.main.business

import bui365.mobile.main.api.Bui365Api
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.model.pojo.FacebookPOJO
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class BlogBusiness {
    private var articles: ArrayList<Article> = ArrayList()
    var loadEnd: Boolean = false
    private val bui365Api by lazy {
        Bui365Api.create()
    }

    fun getArticles(start: Int): Observable<List<Article>> {
        return bui365Api.getBlogArticles(start, 5).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun handleData(result: Any): ArrayList<Article> {
        try {
            val jsonArray = JSONArray(result.toString())
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObject = jsonArray.getJSONObject(i)
                val article = Article()
                article.id = jsonObject.getString("id")
                article.categoryId = jsonObject.getString("categoryId")
                article.categoryName = jsonObject.getString("categoryName")
                article.content = jsonObject.getString("content")
                article.description = jsonObject.getString("description")
                article.image = jsonObject.getString("image")
                article.subjectId = jsonObject.getString("subjectId")
                article.subjectName = jsonObject.getString("subjectName")
                article.title = jsonObject.getString("title")
                article.updatedDate = jsonObject.getString("updatedDate")
                article.url = jsonObject.getString("url")
                articles.add(article)
            }
            loadEnd = length == 0
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return articles
    }

    fun handleFacebookSdk(result: Any): FacebookPOJO {
        val facebookPOJO = FacebookPOJO()

        try {
            val jsonObject = JSONObject(result.toString())
            val ogObject = jsonObject.getJSONObject("og_object")
            val likeObject = ogObject.getJSONObject("likes")
            val summaryObject = likeObject.getJSONObject("summary")
            val shareObject = jsonObject.getJSONObject("share")

            val likes = facebookPOJO.Likes()
            facebookPOJO.likes = likes
            val summary = likes.Summary()
            likes.summary = summary
            summary.likeCount = summaryObject.getInt("total_count")

            val share = facebookPOJO.Share()
            share.shareCount = shareObject.getInt("share_count")
            share.commentCount = shareObject.getInt("comment_count")
            facebookPOJO.share = share

            facebookPOJO.id = jsonObject.getString("id")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return facebookPOJO
    }

    fun isEmptyArticle(result: Any): Boolean {
        return result == ""
    }
}
