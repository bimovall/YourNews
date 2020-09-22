package bivano.apps.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bivano.apps.data.local.converter.DateConverter
import bivano.apps.data.local.dao.AchievedDao
import bivano.apps.data.local.dao.HeadlineDao
import bivano.apps.data.local.entity.AchievedEntity
import bivano.apps.data.local.entity.HeadlineEntity

@Database(entities = [HeadlineEntity::class, AchievedEntity::class], version = 3)
@TypeConverters(DateConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun achievedDao(): AchievedDao
}