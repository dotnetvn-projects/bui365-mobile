package bui365.mobile.main.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


open class Article {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("content")
    @Expose
    var content: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("updatedDate")
    @Expose
    var updatedDate: String? = null
    @SerializedName("categoryId")
    @Expose
    var categoryId: String? = null
    @SerializedName("categoryName")
    @Expose
    var categoryName: String? = null
    @SerializedName("subjectName")
    @Expose
    var subjectName: String? = null
    @SerializedName("subjectId")
    @Expose
    var subjectId: String? = null

    var facebookPOJO: FacebookPOJO = FacebookPOJO()

    override fun toString(): String {
        return "Article(id=$id, title=$title, content=$content, description=$description, url=$url, image=$image, updatedDate=$updatedDate, categoryId=$categoryId, categoryName=$categoryName, subjectName=$subjectName, subjectId=$subjectId, facebookPOJO=$facebookPOJO)"
    }

}
