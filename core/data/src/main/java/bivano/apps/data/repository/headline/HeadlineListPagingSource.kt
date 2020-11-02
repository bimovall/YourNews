package bivano.apps.data.repository.headline

import androidx.paging.PagingSource
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.remote.RemoteDataSource

class HeadlineListPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val category: String?
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageNumber = params.key ?: 1
        when (val response = remoteDataSource.loadHeadline(category, pageNumber)) {
            is Result.Success -> {
                return LoadResult.Page(
                    data = response.data,
                    nextKey = pageNumber + 1,
                    prevKey = null
                )
            }
            else ->
                return LoadResult.Error(
                    if (response is Result.GeneralError) {
                        response.exception
                    } else {
                        Exception((response as Result.ResponseError).failure.message)
                    }
                )
        }
    }
}