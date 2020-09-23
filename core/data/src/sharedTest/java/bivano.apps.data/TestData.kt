package bivano.apps.data

import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.common.response.NewsResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.util.*

fun getSuccessResultData(): Result<List<Article>> {
    val date = Date()
    val article = listOf(
        Article(null, "a", "title", "desc", "", "", date, ""),
        Article(null, "b", "title2", "desc2", "", "", date, "")
    )
    return Result.Success(article)
}

fun getSuccessResponseData(): Response<NewsResponse> {
    val response = NewsResponse(
        "ok",
        10,
        null,
        null,
        listOf(Article(null, "a", "title", "desc", "", "", Date(), ""))
    )
    return Response.success(response)
}

fun getErrorResponseData(): Response<NewsResponse> {
    val response = NewsResponse(
        "error",
        1,
        "apiKeyMissing",
        "error",
        null
    )
    return Response.success(response)
}

fun getErrorRequestData(): Response<NewsResponse> {
    val rawResponse = "{\n" +
            "\"status\": \"error\",\n" +
            "\"code\": \"apiKeyMissing\",\n" +
            "\"message\": \"Error .\"\n" +
            "}"
    return Response.error(500, rawResponse.toResponseBody())
}

fun generateArticle(count: Int): List<Article> {
    val list = mutableListOf<Article>()
    repeat(count) {
        list.add(Article(null, "author $it", "title $it", "desc $it", "abc $it", "", Date(), ""))
    }
    return list.toList()
}