package bivano.apps.data.repository.article

import androidx.paging.PagingSource
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope

class ArticlePagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val query: String,
    private val sort: String
) : PagingSource<Int, Article>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageNumber = params.key ?: 1
        when (val response = remoteDataSource.loadArticles(query, sort, pageNumber)) {
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