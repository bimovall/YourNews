package bivano.apps.homefeature

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel
@ViewModelInject constructor() : ViewModel() {

    val data:MutableLiveData<String> = MutableLiveData()

    fun loadData() {
        data.value = "Load data"
    }
}