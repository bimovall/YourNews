package bivano.apps.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface DataManager {

    suspend fun loadHeadline(category: String?, page: Int): Flow<Result<List<Article>>>

    suspend fun loadArticles(query: String, sort: String, page: Int): Flow<Result<List<Article>>>

    fun initializePagedArticles(
        coroutineScope: CoroutineScope,
        query: String,
        sort: String
    ): LiveData<PagedList<Article>>

    fun getDataSourceFactory(): LiveData<Result<List<Article>>>

    fun search(query: String, sort: String)

    fun loadArchived()
}