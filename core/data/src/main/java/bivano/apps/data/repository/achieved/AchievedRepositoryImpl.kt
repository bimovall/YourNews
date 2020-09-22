package bivano.apps.data.repository.achieved

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import bivano.apps.common.model.Article
import bivano.apps.data.local.LocalDataSource
import javax.inject.Inject

class AchievedRepositoryImpl
@Inject constructor(private val localDataSource: LocalDataSource): AchievedRepository {

    override fun loadAchieved(query: String?): LiveData<PagedList<Article>> {
        val config = PagedList.Config.Builder()
            .setPageSize(15)
            .build()

        val dataSource = localDataSource.loadAchieved(query)

        return LivePagedListBuilder(dataSource, config).build()
    }

    override suspend fun saveNews(article: Article) {
        localDataSource.saveNews(article)
    }

    override suspend fun deleteAchieved(article: Article) {
        localDataSource.deleteAchieved(article)
    }

}