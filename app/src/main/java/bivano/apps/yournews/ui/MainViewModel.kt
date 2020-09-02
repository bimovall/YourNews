package bivano.apps.yournews.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import bivano.apps.domain.DataManager

class MainViewModel
@ViewModelInject constructor(private val dataManager: DataManager) : ViewModel(){


    fun loadData() {

    }

}