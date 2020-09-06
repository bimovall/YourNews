package bivano.apps.yournews.di

import bivano.apps.domain.DataManager
import bivano.apps.domain.di.DefaultDispatcher
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface DynamicModuleDependencies {

    fun dataManager(): DataManager

    @DefaultDispatcher
    fun defaultDispatcher(): CoroutineDispatcher

}