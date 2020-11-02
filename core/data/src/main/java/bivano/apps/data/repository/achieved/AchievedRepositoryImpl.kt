package bivano.apps.data.repository.achieved

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.*
import bivano.apps.common.model.Article
import bivano.apps.data.local.LocalDataSource
import bivano.apps.data.local.mapper.toArticle
import javax.inject.Inject

class AchievedRepositoryImpl
@Inject constructor(private val localDataSource: LocalDataSource): AchievedRepository {

    override fun loadPagingAchieved(query: String?): LiveData<PagingData<Article>> {
        return Pager(
            PagingConfig(pageSize = 20)
        ){
            localDataSource.loadAchieved(query)
        }.liveData.map {
            it.map {data ->
                data.toArticle()
            }
        }
    }

    override suspend fun saveNews(article: Article) {
        localDataSource.saveNews(article)
    }

    override suspend fun deleteAchieved(article: Article) {
        localDataSource.deleteAchieved(article)
    }

}