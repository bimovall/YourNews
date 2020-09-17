package bivano.apps.data.repository.headline

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.local.LocalDataSource
import bivano.apps.data.local.mapper.toHeadlineEntity
import bivano.apps.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HeadlineRepositoryImpl
@Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : HeadlineRepository {

    private lateinit var dataSourceFactory: HeadlinePagedDataFactory

    override suspend fun loadHeadline(category: String?): Flow<Result<List<Article>>> = flow {
        emit(Result.Loading)
        val local = localDataSource.loadHeadline()

        if (local is Result.Success) {
            if (local.data.isNotEmpty())  emit(local)
        }
        when (val remote = remoteDataSource.loadHeadline(category, 1)) {
            is Result.GeneralError -> {
                emit(remote)
            }
            is Result.ResponseError -> {
                emit(remote)
            }
            is Result.Success -> {
                emit(remote)
                localDataSource.saveHeadline(remote.data.map { it.toHeadlineEntity() })
            }
        }
    }

    override fun initializeHeadlinePagedData(
        coroutineScope: CoroutineScope,
        category: String?
    ): LiveData<PagedList<Article>> {
        dataSourceFactory = HeadlinePagedDataFactory(coroutineScope, remoteDataSource, category)

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(15)
            .build()

        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    override fun getInitialNetworkResult(): LiveData<Result<List<Article>>> {
        if (!::dataSourceFactory.isInitialized) throw RuntimeException("You haven't initiated DataSource.Factory ")
        return Transformations.switchMap(
            dataSourceFactory.resultData, HeadlinePagedDataSource::initialStateResultData
        )
    }

    override fun getNetworkResult(): LiveData<Result<List<Article>>> {
        if (!::dataSourceFactory.isInitialized) throw RuntimeException("You haven't initiated DataSource.Factory ")
        return Transformations.switchMap(
            dataSourceFactory.resultData, HeadlinePagedDataSource::stateResultData
        )
    }

    override fun load(category: String?) {
        if (!::dataSourceFactory.isInitialized) throw RuntimeException("You haven't initiated DataSource.Factory ")
        dataSourceFactory.load(category)
    }

}