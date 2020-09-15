package bivano.apps.articlefeature

import androidx.lifecycle.*
import androidx.paging.PagedList
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.repository.article.ArticleRepository
import javax.inject.Inject

class ArticleViewModel
@Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    var networkStateData: LiveData<Result<List<Article>>> = MutableLiveData()

    var initialNetworkStateData: LiveData<Result<List<Article>>> = MutableLiveData()

    var articlePagedData: LiveData<PagedList<Article>> = MutableLiveData()

    init {
        articlePagedData = articleRepository.initializeArticlesPagedArticles(viewModelScope, "", "relevancy")
        initialNetworkStateData = articleRepository.getInitialNetworkResult()
        networkStateData = articleRepository.getNetworkResult()
    }

    fun loadArticle(query: String, sort: String) {
        articleRepository.search(query, sort)
        articlePagedData.value?.dataSource?.invalidate()
    }
}