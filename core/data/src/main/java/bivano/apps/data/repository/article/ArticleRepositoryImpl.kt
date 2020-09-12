package bivano.apps.data.repository.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import java.lang.RuntimeException
import javax.inject.Inject

class ArticleRepositoryImpl
@Inject constructor(private val remoteDataSource: RemoteDataSource) : ArticleRepository {

    private lateinit var dataSourceFactory: ArticlePagedDataFactory

    override fun initializeArticlesPagedArticles(
        coroutineScope: CoroutineScope,
        query: String,
        sort: String
    ): LiveData<PagedList<Article>> {
        dataSourceFactory =
            ArticlePagedDataFactory(
                coroutineScope = coroutineScope,
                remoteDataSource = remoteDataSource,
                query = query,
                category = sort
            )
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(15)
            .build()

        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    override fun getNetworkResult(): LiveData<Result<List<Article>>> {
        if (!::dataSourceFactory.isInitialized) throw RuntimeException("You haven't initiated DataSource.Factory ")
        return Transformations.switchMap(
            dataSourceFactory.resultData, ArticlePagedDataSource::stateResultData
        )
    }

    override fun search(query: String, sort: String) {
        if (!::dataSourceFactory.isInitialized) throw RuntimeException("You haven't initiated DataSource.Factory ")
        dataSourceFactory.search(query, sort)
    }
}