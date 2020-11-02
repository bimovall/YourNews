package bivano.apps.articlefeature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import bivano.apps.common.model.Article
import bivano.apps.data.repository.achieved.AchievedRepository
import bivano.apps.data.repository.article.ArticleRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleViewModel
@Inject constructor(
    private val articleRepository: ArticleRepository,
    private val achievedRepository: AchievedRepository
) : ViewModel() {

    private val querySortData: MutableLiveData<Pair<String, String>> = MutableLiveData()

    val articlePagingData = Transformations.switchMap(querySortData) {
        articleRepository.initSearchArticlePagingData(it.first, it.second)
            .cachedIn(viewModelScope)
    }

    init {
        loadArticle("", "relevancy")
    }

    fun loadArticle(query: String, sort: String) {
        querySortData.value = Pair(query, sort)
    }

    fun saveNews(article: Article) {
        viewModelScope.launch {
            achievedRepository.saveNews(article)
        }
    }
}