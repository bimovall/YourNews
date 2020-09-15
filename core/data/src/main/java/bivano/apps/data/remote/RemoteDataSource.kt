package bivano.apps.data.remote

import bivano.apps.common.Failure
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.common.response.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource
@Inject constructor(private val newsService: NewsService) {
    //TODO add function based on interface (abstraction)

    suspend fun loadHeadline(category: String?, page: Int): Result<List<Article>> {
        return try {
            val response = newsService.getHeadlines("id", category, page, 15)
            getResult(response = response, onError = {
                Result.GeneralError(Exception("Error getting response : ${response.code()} ${response.message()}"))
            }, onErrorResponse = {
                Result.ResponseError(it)
            })
        } catch (e: Exception) {
            Result.GeneralError(e)
        }
    }

    suspend fun loadArticles(query: String, sort: String, page: Int): Result<List<Article>> {
        return try {
            val response = newsService.getArticle(query, sort, "id", page, 15)
            getResult(response = response, onError = {
                Result.GeneralError(Exception("Error getting response : ${response.code()} ${response.message()}"))
            }, onErrorResponse = {
                Result.ResponseError(it)
            })
        } catch (e: Exception) {
            Result.GeneralError(e)
        }

    }

    private inline fun getResult(
        response: Response<NewsResponse>,
        onError: () -> Result.GeneralError,
        onErrorResponse: (Failure) -> Result.ResponseError
    ): Result<List<Article>> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return if (body.code == null) {
                    Result.Success(body.articles!!)
                } else {
                    onErrorResponse.invoke(Failure(body.status, body.message, body.code))
                }
            }
        }
        return onError.invoke()
    }
}