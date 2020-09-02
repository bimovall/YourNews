package bivano.apps.data.remote

import bivano.apps.common.Failure
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.common.response.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource
@Inject constructor(private val newsService: NewsService) {

    suspend fun loadHeadline(category: String?, page: Int): Result<List<Article>> {
        val response = newsService.getHeadlines("id", category, page, 15)
        return getResult(response = response, onError = {
            Result.GeneralError(Exception("Error getting response : ${response.code()} ${response.message()}"))
        }, onErrorResponse = {
            Result.ResponseError(it)
        })
    }

    private inline  fun getResult(
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