package bivano.apps.articlefeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.repository.article.ArticleRepository
import javax.inject.Inject

class ArticleViewModel
@Inject constructor(
    private val articleRepository: ArticleRepository
    //private val loadArticlesUseCase: LoadArticlesPagedUseCase
) : ViewModel() {

    var networkStateData: LiveData<Result<List<Article>>> = MutableLiveData()

    var articlePagedData: LiveData<PagedList<Article>> = MutableLiveData()

    init {
        articlePagedData = articleRepository.initializeArticlesPagedArticles(viewModelScope, "", "relevancy")
        networkStateData = articleRepository.getNetworkResult()
        /*articlePagedData =
            loadArticlesUseCase.initializePagedList(viewModelScope, "abc", "relevancy")
        networkStateData = loadArticlesUseCase.getNetworkState()*/
    }

    fun loadArticle(query: String, sort: String) {
        articleRepository.search(query, sort)
        articlePagedData.value?.dataSource?.invalidate()
        //loadArticlesUseCase.search(query, sort)
        //articlePagedData.value?.dataSource?.invalidate()
    }
}