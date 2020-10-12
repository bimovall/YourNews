package bivano.apps.homefeature

import androidx.lifecycle.*
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.repository.achieved.AchievedRepository
import bivano.apps.data.repository.headline.HeadlineRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val headlineRepository: HeadlineRepository,
    private val achievedRepository: AchievedRepository

) : ViewModel() {
    private val categoryData: MutableLiveData<String> = MutableLiveData()

    val stateNetworkData: LiveData<Result<List<Article>>> = categoryData.switchMap {
        headlineRepository.loadHeadline(it).asLiveData()
    }

    init {
        loadData(null)
    }

    fun loadData(category: String?) {
        categoryData.value = category
    }

    fun saveNews(article: Article) {
        viewModelScope.launch {
            achievedRepository.saveNews(article)
        }
    }
}