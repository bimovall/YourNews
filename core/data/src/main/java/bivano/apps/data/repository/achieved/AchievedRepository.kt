package bivano.apps.data.repository.achieved

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import bivano.apps.common.model.Article

interface AchievedRepository {

    fun loadAchieved(query: String?): LiveData<PagedList<Article>>

    suspend fun saveNews(article: Article)

    suspend fun deleteAchieved(article: Article)
}