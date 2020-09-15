package bivano.apps.data.repository.headline

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeadlinePagedDataSource(
    private val coroutineScope: CoroutineScope,
    private val remoteDataSource: RemoteDataSource,
    private val category: String?
) : PageKeyedDataSource<Int, Article>() {

    val initialStateResultData = MutableLiveData<Result<List<Article>>>()
    val stateResultData = MutableLiveData<Result<List<Article>>>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            initialStateResultData.postValue(Result.Loading)
            val response = remoteDataSource.loadHeadline(category, 1)
            if (response is Result.Success) {
                callback.onResult(response.data, null, 2)
            }
            initialStateResultData.postValue(response)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        coroutineScope.launch {
            stateResultData.value = Result.Loading
            val response = remoteDataSource.loadHeadline(category, params.key)
            if (response is Result.Success) {
                callback.onResult(response.data, params.key + 1)
            }
            stateResultData.value = response
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    }
}