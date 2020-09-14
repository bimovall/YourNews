package bivano.apps.data.repository.article

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import kotlinx.coroutines.CoroutineScope

interface ArticleRepository {

    fun initializeArticlesPagedArticles(
        coroutineScope: CoroutineScope,
        query: String,
        sort: String
    ): LiveData<PagedList<Article>>

    fun getInitialNetworkResult(): LiveData<Result<List<Article>>>

    fun getNetworkResult(): LiveData<Result<List<Article>>>

    fun search(query: String, sort: String)

}