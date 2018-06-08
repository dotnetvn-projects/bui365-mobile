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

    /** get list of Blog Article
     * @param start: paging
     * @param take: number of item displayed
     */
    @GET("get-articles")
    fun getBlogArticles(@Query("start") start: Int,
                        @Query("take") take: Int): Observable<List<Article>>

    /** get list of Search Result
     * @param search: user's input
     */
    @GET("get-articles")
    fun searchArticles(@Query("search") search: String): Observable<List<Article>>

    //get detail article based on article's id
    @GET("get-article-detail")
    fun getDetailArticle(@Query("id") id: String): Observable<Article>

    /** get list of Handbook Article
     * @param start: paging
     * @param take: number of item displayed
     * @param cate: handbook's category
     */
    @GET("get-articles")
    fun getHandbookArticle(@Query("start") start: Int,
                           @Query("take") take: Int,
                           @Query("cate") cate: String): Observable<List<Article>>

    // create retrofit static instance
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