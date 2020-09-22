package bivano.apps.achievedfeature

import androidx.lifecycle.*
import androidx.paging.PagedList
import bivano.apps.common.model.Article
import bivano.apps.data.repository.achieved.AchievedRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AchievedViewModel
@Inject constructor(private val achievedRepository: AchievedRepository) : ViewModel() {

    private val keywordData: MutableLiveData<String?> = MutableLiveData()

    val resultData: LiveData<PagedList<Article>> = Transformations.switchMap(keywordData) {
        achievedRepository.loadAchieved(it)
    }

    init {
        search("")
    }

    fun search(keyword: String) {
        keywordData.value = keyword
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            achievedRepository.deleteAchieved(article)
        }
    }
}