package bivano.apps.achievedfeature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bivano.apps.common.model.Article
import bivano.apps.data.repository.achieved.AchievedRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AchievedViewModel
@Inject constructor(private val achievedRepository: AchievedRepository) : ViewModel() {

    private val keywordData: MutableLiveData<String?> = MutableLiveData()

    val achievedPagingData = Transformations.switchMap(keywordData) {
        achievedRepository.loadPagingAchieved(it)
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