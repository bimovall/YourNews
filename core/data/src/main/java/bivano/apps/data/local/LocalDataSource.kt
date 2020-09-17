package bivano.apps.data.local

import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.data.local.entity.HeadlineEntity

interface LocalDataSource {

    suspend fun loadHeadline(): Result<List<Article>>

    suspend fun saveHeadline(headlines: List<HeadlineEntity>)
}