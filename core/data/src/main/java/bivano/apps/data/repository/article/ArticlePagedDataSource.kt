package bivano.apps.data.repository.article

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.common.model.Source
import bivano.apps.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ArticlePagedDataSource(
    private val coroutineScope: CoroutineScope,
    private val remoteDataSource: RemoteDataSource,
    private val query: String,
    private val category: String
) : PageKeyedDataSource<Int, Article>() {

    val stateResultData = MutableLiveData<Result<List<Article>>>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        coroutineScope.launch(Dispatchers.IO) {
           stateResultData.postValue(Result.Loading)
            val response = remoteDataSource.loadArticles(query, category, 1)
            if (response is  Result.Success) {
                callback.onResult(response.data, null, 2)
            }
            stateResultData.postValue(response)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

        coroutineScope.launch {
            stateResultData.value = Result.Loading
            val response = remoteDataSource.loadArticles(query, category, params.key)
            if (response is  Result.Success) {
                callback.onResult(response.data, params.key + 1)
            } else {
                stateResultData.value = response
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

    }
}