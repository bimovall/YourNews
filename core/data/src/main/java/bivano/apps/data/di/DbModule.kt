package bivano.apps.data.di

import android.content.Context
import androidx.room.Room
import bivano.apps.data.local.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context) =
        Room.databaseBuilder(application, NewsDatabase::class.java, "news.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideHeadlineDao(newsDatabase: NewsDatabase) = newsDatabase.headlineDao()

    @Provides
    @Singleton
    fun provideAchievedDao(newsDatabase: NewsDatabase) = newsDatabase.achievedDao()
}