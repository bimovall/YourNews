package bivano.apps.data.repository.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.*
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import java.lang.RuntimeException
import javax.inject.Inject

class ArticleRepositoryImpl
@Inject constructor(private val remoteDataSource: RemoteDataSource) : ArticleRepository {

    override fun initSearchArticlePagingData(query: String, sort: String): LiveData<PagingData<Article>> {
        return Pager(
            PagingConfig(pageSize = 15)
        ) {
            ArticlePagingSource(remoteDataSource,query, sort)
        }.liveData
    }
}