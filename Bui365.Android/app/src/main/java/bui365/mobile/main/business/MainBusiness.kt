package bui365.mobile.main.business

import bui365.mobile.main.model.pojo.Article
import org.json.JSONArray
import org.json.JSONException


class MainBusiness {
    private var articles: ArrayList<Article> = ArrayList()

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
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return articles
    }

    fun isEmptyArticle(result: Any): Boolean {
        return result == ""
    }
}
