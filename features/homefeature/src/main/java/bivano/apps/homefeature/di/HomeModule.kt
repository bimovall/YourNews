package bivano.apps.homefeature.di

import androidx.lifecycle.ViewModel
import bivano.apps.common.di.ViewModelKey
import bivano.apps.homefeature.HomeViewModel
import bivano.apps.homefeature.list.ListHeadlineViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(FragmentComponent::class)
abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListHeadlineViewModel::class)
    abstract fun bindListHeadlineViewModel(listHeadlineViewModel: ListHeadlineViewModel): ViewModel

}