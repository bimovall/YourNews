package bivano.apps.data

import bivano.apps.common.Result
import bivano.apps.data.remote.RemoteDataSource
import bivano.apps.domain.DataManager
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataManagerImpl
@Inject constructor(private val remoteDataSource: RemoteDataSource) :
    DataManager {
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
    }

    override fun loadArticles() {
        TODO("Not yet implemented")
    }

    override fun loadArchived() {
        TODO("Not yet implemented")
    }
}