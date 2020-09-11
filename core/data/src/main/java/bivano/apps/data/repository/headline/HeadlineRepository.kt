package bivano.apps.data.repository.headline

import bivano.apps.common.Result
import bivano.apps.common.model.Article
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {

    suspend fun loadHeadline(category: String?): Flow<Result<List<Article>>>
}