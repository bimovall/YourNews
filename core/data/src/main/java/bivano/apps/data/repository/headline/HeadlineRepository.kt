package bivano.apps.data.repository.headline

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {

    suspend fun loadHeadline(category: String?): Flow<Result<List<Article>>>

    fun initializeHeadlinePagedData(
        coroutineScope: CoroutineScope,
        category: String?
    ): LiveData<PagedList<Article>>

    fun getInitialNetworkResult(): LiveData<Result<List<Article>>>

    fun getNetworkResult(): LiveData<Result<List<Article>>>

    fun load(category: String?)

}