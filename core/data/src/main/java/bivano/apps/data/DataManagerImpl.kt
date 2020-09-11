package bivano.apps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.remote.RemoteDataSource
import bivano.apps.data.repository.article.ArticlePagedDataFactory
import bivano.apps.data.repository.article.ArticlePagedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataManagerImpl
@Inject constructor(private val remoteDataSource: RemoteDataSource) :
    DataManager {

    lateinit var dataFactory2: ArticlePagedDataFactory

    override suspend fun loadHeadline(category: String?, page: Int) = flow {
        emit(Result.Loading)
        val remote = remoteDataSource.loadHeadline(category, page)
        when (remote) {
            is Result.GeneralError -> {
                emit(remote)
            }
            is Result.ResponseError -> {
                emit(remote)
            }
            is Result.Success -> {
                emit(remote)
            }

        }
        //TODO caching the request
    }

    override suspend fun loadArticles(
        query: String,
        sort: String,
        page: Int
    ): Flow<Result<List<Article>>> = flow {
        emit(Result.Loading)
        val remote = remoteDataSource.loadArticles(query, sort, page)
        when (remote) {
            is Result.GeneralError -> {
                emit(remote)
            }
            is Result.ResponseError -> {
                emit(remote)
            }
            is Result.Success -> {
                emit(remote)
            }
        }
        //TODO caching the request
    }

    override fun initializePagedArticles(
        coroutineScope: CoroutineScope,
        query: String,
        sort: String
    ): LiveData<PagedList<Article>> {
        val dataSourceFactory =
            ArticlePagedDataFactory(
                coroutineScope = coroutineScope,
                remoteDataSource = remoteDataSource,
                query = query,
                category = sort
            )
        dataFactory2 = dataSourceFactory
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(15)
            .build()

        /*val state = Transformations.switchMap(
            dataSourceFactory.resultData, ArticlePagedDataSource::stateResultData
        )*/

        //TODO send to view model
        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    override fun getDataSourceFactory(): LiveData<Result<List<Article>>> {
        return Transformations.switchMap(
            dataFactory2.resultData, ArticlePagedDataSource::stateResultData)
    }

    override fun search(
        query: String,
        sort: String
    ) {
        dataFactory2.search(query, sort)
    }


    override fun loadArchived() {
        TODO("Not yet implemented")
    }
}