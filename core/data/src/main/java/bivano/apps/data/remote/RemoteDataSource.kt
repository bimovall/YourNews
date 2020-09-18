package bivano.apps.data.remote

import bivano.apps.common.Failure
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.common.response.NewsResponse
import retrofit2.Response
import javax.inject.Inject

interface RemoteDataSource {

    suspend fun loadHeadline(category: String?, page: Int): Result<List<Article>>

    suspend fun loadArticles(query: String, sort: String, page: Int): Result<List<Article>>
}