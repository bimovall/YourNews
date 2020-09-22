package bivano.apps.data.local

import androidx.paging.DataSource
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.local.dao.AchievedDao
import bivano.apps.data.local.dao.HeadlineDao
import bivano.apps.data.local.entity.HeadlineEntity
import bivano.apps.data.local.mapper.toAchievedEntity
import bivano.apps.data.local.mapper.toArticle
import javax.inject.Inject

class LocalDataSourceImpl
@Inject constructor(
    private val headlineDao: HeadlineDao,
    private val achievedDao: AchievedDao
) : LocalDataSource {

    override suspend fun loadHeadline(): Result<List<Article>> {
        return try {
            val response = headlineDao.getHeadlines().map {
                it.toArticle()
            }
            Result.Success(response)
        } catch (e: Exception) {
            Result.GeneralError(e)
        }
    }

    override suspend fun saveHeadline(headlines: List<HeadlineEntity>) {
        try {
            headlineDao.insertHeadline(headlines)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadAchieved(query: String?): DataSource.Factory<Int, Article> {
        return if (query.isNullOrBlank()) {
            achievedDao.getAchievedData().map {
                it.toArticle()
            }
        } else {
            achievedDao.searchAchievedData("%$query%").map {
                it.toArticle()
            }
        }
    }

    override suspend fun saveNews(article: Article) {
        try {
            val resultExists = achievedDao.isAchievedDataExists(article.url)
            if (resultExists) return
            achievedDao.insertData(article.toAchievedEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteAchieved(article: Article) {
        try {
            achievedDao.deleteData(article.url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}