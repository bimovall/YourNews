package bivano.apps.common.response

import bivano.apps.common.model.Article

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val code: String? = null,
    val message: String? = null,
    val articles: List<Article>? = null
)