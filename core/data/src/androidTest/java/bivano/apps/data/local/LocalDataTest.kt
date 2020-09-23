package bivano.apps.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import bivano.apps.data.generateArticle
import bivano.apps.data.local.dao.AchievedDao
import bivano.apps.data.local.dao.HeadlineDao
import bivano.apps.data.local.db.NewsDatabase
import bivano.apps.data.local.mapper.toAchievedEntity
import bivano.apps.data.local.mapper.toHeadlineEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDataTest {

    private lateinit var headlineDao: HeadlineDao

    private lateinit var achievedDao: AchievedDao

    private lateinit var db: NewsDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        headlineDao = db.headlineDao()
        achievedDao = db.achievedDao()
    }

    @After
    fun destroy() {
        db.close()
    }

    @Test
    fun replaceHeadlineAndVerify() = runBlocking {
        val prevData = generateArticle(10).map { it.toHeadlineEntity() }
        headlineDao.insertData(prevData)

        val data = generateArticle(5).map { it.toHeadlineEntity() }
        headlineDao.insertHeadline(data)

        val result = headlineDao.getHeadlines()
        Assert.assertEquals(5, result.size)
    }

    @Test
    fun loadHeadlineAndVerify() = runBlocking {
        val data = generateArticle(15).map { it.toHeadlineEntity() }
        headlineDao.insertData(data)

        val result = headlineDao.getHeadlines()
        Assert.assertEquals(15, result.size)
    }

    @Test
    fun saveNewsAchievedAndVerify() = runBlocking {
        val data = generateArticle(1).map { it.toAchievedEntity() }[0]
        achievedDao.insertData(data)

        val result = achievedDao.isAchievedDataExists("abc 0")
        Assert.assertTrue(result)
    }

    @Test
    fun deleteNewsAchievedAndVerify() = runBlocking {
        val data = generateArticle(1).map { it.toAchievedEntity() }[0]
        achievedDao.insertData(data)
        achievedDao.deleteData("abc 0")

        val check = achievedDao.isAchievedDataExists("abc 0")
        Assert.assertTrue(!check)

    }
}