package bivano.apps.data.repository.article

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import bivano.apps.common.model.Article

interface ArticleRepository {

    fun initSearchArticlePagingData(query: String, sort: String): LiveData<PagingData<Article>>

}