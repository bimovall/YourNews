package bivano.apps.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bivano.apps.data.local.entity.AchievedEntity

@Dao
interface AchievedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: AchievedEntity)

    @Query("DELETE FROM achieved WHERE url = :url")
    suspend fun deleteData(url: String)

    @Query("SELECT * FROM achieved ORDER BY id DESC")
    fun getAchievedData(): PagingSource<Int, AchievedEntity>

    @Query("SELECT EXISTS (SELECT * FROM achieved WHERE url = :url)")
    suspend fun isAchievedDataExists(url: String): Boolean

    @Query("SELECT * FROM achieved WHERE title LIKE :query ORDER BY id DESC")
    fun searchAchievedData(query: String): PagingSource<Int, AchievedEntity>
}