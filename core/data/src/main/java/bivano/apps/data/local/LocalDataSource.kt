package bivano.apps.data.local

import androidx.paging.DataSource
import androidx.paging.PagingSource
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.local.entity.AchievedEntity
import bivano.apps.data.local.entity.HeadlineEntity

interface LocalDataSource {

    suspend fun loadHeadline(): Result<List<Article>>

    suspend fun saveHeadline(headlines: List<HeadlineEntity>)

    fun loadAchieved(query: String?): PagingSource<Int, AchievedEntity>

    suspend fun saveNews(article: Article)

    suspend fun deleteAchieved(article: Article)
}