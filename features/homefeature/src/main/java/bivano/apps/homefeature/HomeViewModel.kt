package bivano.apps.homefeature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.repository.headline.HeadlineRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val headlineRepository: HeadlineRepository

) : ViewModel() {

    val articleData: MutableLiveData<List<Article>> = MutableLiveData()

    val featuredData: MutableLiveData<List<Article>> = MutableLiveData()

    @ExperimentalCoroutinesApi
    fun loadData(category: String?) {
        //TODO handle error response
        viewModelScope.launch {
            headlineRepository.loadHeadline(category).collect {
                when (it) {
                    is Result.Success -> {
                        if (it.data.size > 4) {
                            featuredData.value = it.data.subList(0, 5)
                            articleData.value = it.data.subList(5, it.data.size)
                        } else {
                            featuredData.value = it.data
                            articleData.value = listOf()
                        }
                    }

                    is Result.ResponseError -> {
                        println("Check load ResponseError : ${it.failure}")
                    }
                    is Result.GeneralError -> {
                        println("Check load ResponseError : ${it.exception}")
                    }
                }
            }
        }
    }
}