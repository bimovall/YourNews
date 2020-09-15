package bivano.apps.data.repository.headline

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import bivano.apps.common.model.Article
import bivano.apps.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope

class HeadlinePagedDataFactory(
    private val coroutineScope: CoroutineScope,
    private val remoteDataSource: RemoteDataSource,
    private var category: String?
) : DataSource.Factory<Int, Article>() {

    val resultData = MutableLiveData<HeadlinePagedDataSource>()

    override fun create(): DataSource<Int, Article> {
        val dataSource = HeadlinePagedDataSource(coroutineScope, remoteDataSource, category)
        resultData.postValue(dataSource)
        return dataSource
    }

    fun load(category: String?) {
        this.category = category
    }
}