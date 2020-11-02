package bivano.apps.data.repository.headline

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {

    fun loadHeadline(category: String?): Flow<Result<List<Article>>>

    fun initListHeadlinePagingData(category: String?): LiveData<PagingData<Article>>

}