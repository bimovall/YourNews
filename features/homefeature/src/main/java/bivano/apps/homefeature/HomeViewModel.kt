package bivano.apps.homefeature

import androidx.lifecycle.*
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.repository.headline.HeadlineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val headlineRepository: HeadlineRepository

) : ViewModel() {
    private val categoryData: MutableLiveData<String> = MutableLiveData()

    val stateNetworkData: LiveData<Result<List<Article>>> =
        Transformations.switchMap(categoryData) {
            liveData(Dispatchers.IO) {
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
}