package bivano.apps.domain

import bivano.apps.common.Result
import bivano.apps.common.model.Article
import kotlinx.coroutines.flow.Flow

interface DataManager {

    suspend fun loadHeadline(category: String?, page: Int) : Flow<Result<List<Article>>>

    fun loadArticles()

    fun loadArchived()
}