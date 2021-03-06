package bivano.apps.data.di

import bivano.apps.data.local.LocalDataSource
import bivano.apps.data.local.LocalDataSourceImpl
import bivano.apps.data.remote.RemoteDataSource
import bivano.apps.data.remote.RemoteDataSourceImpl
import bivano.apps.data.repository.achieved.AchievedRepository
import bivano.apps.data.repository.achieved.AchievedRepositoryImpl
import bivano.apps.data.repository.article.ArticleRepository
import bivano.apps.data.repository.article.ArticleRepositoryImpl
import bivano.apps.data.repository.headline.HeadlineRepository
import bivano.apps.data.repository.headline.HeadlineRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    /*@Singleton
    @Binds
    abstract fun bindsDataManager(dataManager: DataManagerImpl): DataManager*/

    @Singleton
    @Binds
    abstract fun bindsLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Singleton
    @Binds
    abstract fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository

    @Singleton
    @Binds
    abstract fun bindsHeadlineRepository(headlineRepositoryImpl: HeadlineRepositoryImpl): HeadlineRepository

    @Singleton
    @Binds
    abstract fun bindsAchievedRepository(repositoryImpl: AchievedRepositoryImpl): AchievedRepository

}