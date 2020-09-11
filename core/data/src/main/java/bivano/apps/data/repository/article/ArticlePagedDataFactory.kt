package bivano.apps.data.repository.article

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import bivano.apps.common.model.Article
import bivano.apps.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope

class ArticlePagedDataFactory(
    private val coroutineScope: CoroutineScope,
    private val remoteDataSource: RemoteDataSource,
    private var query: String,
    private var category: String
) : DataSource.Factory<Int, Article>() {

    val resultData = MutableLiveData<ArticlePagedDataSource>()

    override fun create(): DataSource<Int, Article> {
        val dataSource =
            ArticlePagedDataSource(
                coroutineScope,
                remoteDataSource,
                query,
                category
            )
        resultData.postValue(dataSource)
        return dataSource
    }

    fun search(query: String, category: String) {
        this.query = query
        this.category = category
    }
}