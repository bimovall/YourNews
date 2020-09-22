package bivano.apps.achievedfeature.di

import androidx.lifecycle.ViewModel
import bivano.apps.achievedfeature.AchievedViewModel
import bivano.apps.common.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(FragmentComponent::class)
abstract class AchievedModule {

    @Binds
    @IntoMap
    @ViewModelKey(AchievedViewModel::class)
    abstract fun bindAchievedViewModel(achievedViewModel: AchievedViewModel): ViewModel

}