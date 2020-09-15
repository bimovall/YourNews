package bivano.apps.homefeature.list

import androidx.lifecycle.*
import androidx.paging.PagedList
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.repository.headline.HeadlineRepository
import javax.inject.Inject

class ListHeadlineViewModel
@Inject constructor(
    private val headlineRepository: HeadlineRepository
): ViewModel() {

    private val categoryData = MutableLiveData<String>()

    val headlinePagedData: LiveData<PagedList<Article>> = Transformations.switchMap(categoryData) {
        headlineRepository.initializeHeadlinePagedData(viewModelScope, it)
    }

    val networkStateData: LiveData<Result<List<Article>>> = Transformations.switchMap(headlinePagedData) {
        headlineRepository.getNetworkResult()}

    val initialNetworkStateData: LiveData<Result<List<Article>>> = Transformations.switchMap(headlinePagedData) {
        headlineRepository.getInitialNetworkResult()
    }


    init {
        //headlinePagedData = headlineRepository.initializeHeadlinePagedData(viewModelScope, "general")
        /*initialNetworkStateData = headlineRepository.getInitialNetworkResult()
        networkStateData = headlineRepository.getNetworkResult()*/

    }


    fun initLoad(category: String?) {
        if (categoryData.value != category){
            categoryData.value = category
        }
    }

    fun load(category: String?) {
        headlineRepository.load(category)
        headlinePagedData.value?.dataSource?.invalidate()
    }
}