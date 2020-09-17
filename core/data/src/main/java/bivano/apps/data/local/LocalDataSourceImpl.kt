package bivano.apps.data.local

import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.local.dao.HeadlineDao
import bivano.apps.data.local.entity.HeadlineEntity
import bivano.apps.data.local.mapper.toArticle
import javax.inject.Inject

class LocalDataSourceImpl
@Inject constructor(private val headlineDao: HeadlineDao) : LocalDataSource {

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
}