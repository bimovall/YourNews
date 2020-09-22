package bivano.apps.data

import bivano.apps.common.Result
import bivano.apps.common.model.Article
import java.util.*

fun getSuccessResultData(): Result<List<Article>> {
    val date = Date()
    val article = listOf(
        Article(null, "a", "title", "desc", "","", date,""),
        Article(null, "b", "title2", "desc2", "","", date,"")
    )
    return Result.Success(article)
}