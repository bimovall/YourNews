package bivano.apps.data.repository.headline

import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HeadlineRepositoryImpl
@Inject constructor(private val remoteDataSource: RemoteDataSource) : HeadlineRepository {
    override suspend fun loadHeadline(category: String?): Flow<Result<List<Article>>> = flow {
        emit(Result.Loading)
        val remote = remoteDataSource.loadHeadline(category, 1)
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
}