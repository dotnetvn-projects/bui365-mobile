package bui365.mobile.main.api

import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.util.Config
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Bui365Api {
    /**
     * GET Method
     */

    //get Blog Article
    @GET("get-articles")
    fun getBlogArticles(@Query("start") start: Int,
                        @Query("take") take: Int): Observable<List<Article>>

    @GET("get-articles")
    fun searchArticles(@Query("search") search: String): Observable<List<Article>>

    @GET("get-article-detail")
    fun getDetailArticle(@Query("id") id: String): Observable<Article>


    /**
     * create Retrofit instance
     */
    companion object {
        fun create(): Bui365Api {
            val okHttpClient = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Config.BUI_SERVER)
                    .client(okHttpClient)
                    .build()
            return retrofit.create(Bui365Api::class.java)
        }
    }
}