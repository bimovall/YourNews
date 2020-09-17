package bivano.apps.data.local.mapper

import bivano.apps.common.model.Article
import bivano.apps.data.local.entity.HeadlineEntity


fun HeadlineEntity.toArticle() =
    Article(null, author, title, description, url, urlToImage, publishedAt, content)

fun Article.toHeadlineEntity() =
    HeadlineEntity(
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )