package bivano.apps.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "headline_cache")
data class HeadlineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String? = null,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date?,
    val content: String? = null
)