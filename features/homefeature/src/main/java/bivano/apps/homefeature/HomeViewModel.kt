package bivano.apps.homefeature

import androidx.lifecycle.*
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.repository.achieved.AchievedRepository
import bivano.apps.data.repository.headline.HeadlineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val headlineRepository: HeadlineRepository,
    private val achievedRepository: AchievedRepository

) : ViewModel() {
    private val categoryData: MutableLiveData<String> = MutableLiveData()

    val stateNetworkData: LiveData<Result<List<Article>>> =
        Transformations.switchMap(categoryData) {
            liveData {
                headlineRepository.loadHeadline(it).collect {
                    emit(it)
                }
            }
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