package bivano.apps.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "achieved")
data class AchievedEntity(
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