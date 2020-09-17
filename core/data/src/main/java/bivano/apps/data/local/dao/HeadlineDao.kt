package bivano.apps.data.local.dao

import androidx.room.*
import bivano.apps.data.local.entity.HeadlineEntity

@Dao
interface HeadlineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<HeadlineEntity>)

    @Query("SELECT * FROM headline_cache")
    suspend fun getHeadlines(): List<HeadlineEntity>

    @Query("DELETE FROM headline_cache")
    fun deleteHeadline()

    @Transaction
    fun insertHeadline(data: List<HeadlineEntity>) {
        deleteHeadline()
        insertData(data)
    }
}