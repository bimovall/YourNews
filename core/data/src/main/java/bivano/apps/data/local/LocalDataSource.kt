package bivano.apps.data.local

import androidx.paging.DataSource
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.local.entity.HeadlineEntity

interface LocalDataSource {

    suspend fun loadHeadline(): Result<List<Article>>

    suspend fun saveHeadline(headlines: List<HeadlineEntity>)

    fun loadAchieved(query: String?): DataSource.Factory<Int, Article>

    suspend fun saveNews(article: Article)

    suspend fun deleteAchieved(article: Article)
}