package bivano.apps.data.remote

import bivano.apps.common.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("category") category: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun searchHeadlines(
        @Query("q") query: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<NewsResponse>
}