package bivano.apps.yournews.di

import bivano.apps.data.repository.achieved.AchievedRepository
import bivano.apps.data.repository.article.ArticleRepository
import bivano.apps.data.repository.headline.HeadlineRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface DynamicModuleDependencies {

    //fun dataManager(): DataManager

    fun articleRepository(): ArticleRepository

    fun headlineRepository(): HeadlineRepository

    fun achievedRepository(): AchievedRepository

    /*@DefaultDispatcher
    fun defaultDispatcher(): CoroutineDispatcher*/

}