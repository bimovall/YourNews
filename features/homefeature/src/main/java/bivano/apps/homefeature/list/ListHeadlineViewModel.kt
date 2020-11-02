package bivano.apps.homefeature.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import bivano.apps.common.model.Article
import bivano.apps.data.repository.achieved.AchievedRepository
import bivano.apps.data.repository.headline.HeadlineRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListHeadlineViewModel
@Inject constructor(
    private val headlineRepository: HeadlineRepository,
    private val achievedRepository: AchievedRepository
) : ViewModel() {

    private val categoryData = MutableLiveData<String>()

    val headlinePaging = Transformations.switchMap(categoryData) {
        headlineRepository.initListHeadlinePagingData(it)
            .cachedIn(viewModelScope)
    }

    fun initLoad(category: String?) {
        if (categoryData.value != category) {
            categoryData.value = category
        }
    }

    fun saveNews(article: Article) {
        viewModelScope.launch {
            achievedRepository.saveNews(article)
        }
    }
}