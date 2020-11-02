package bivano.apps.data.repository.achieved

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import bivano.apps.common.model.Article

interface AchievedRepository {

    fun loadPagingAchieved(query: String?): LiveData<PagingData<Article>>

    suspend fun saveNews(article: Article)

    suspend fun deleteAchieved(article: Article)
}