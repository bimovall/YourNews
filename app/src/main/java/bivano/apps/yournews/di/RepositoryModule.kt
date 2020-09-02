package bivano.apps.yournews.di

import bivano.apps.domain.DataManager
import bivano.apps.data.DataManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsDataManager(dataManager: DataManagerImpl): DataManager
}