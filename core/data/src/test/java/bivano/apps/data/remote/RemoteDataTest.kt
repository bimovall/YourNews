package bivano.apps.data.remote

import bivano.apps.common.Failure
import bivano.apps.common.Result
import bivano.apps.data.getSuccessResultData
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class RemoteDataTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun showSuccessResultLoadArticle() = runBlocking {
        Mockito.`when`(remoteDataSource.loadArticles("", "", 1))
            .thenReturn(getSuccessResultData())
        val result = remoteDataSource.loadArticles("", "", 1)
        Mockito.verify(remoteDataSource).loadArticles("", "", 1)
        assert(result is Result.Success)
        val size = (result as Result.Success).data.size
        Assert.assertEquals(2, size)
    }

    @Test
    fun showResponseErrorLoadArticle() = runBlocking {
        val failure = Failure("", "Response Error", "400")
        Mockito.`when`(remoteDataSource.loadArticles("", "", 1))
            .thenReturn(Result.ResponseError(failure))
        val result = remoteDataSource.loadArticles("", "", 1)
        Mockito.verify(remoteDataSource).loadArticles("", "", 1)
        assert(result is Result.ResponseError)

    }

    @Test
    fun showGeneralErrorLoadArticle() = runBlocking {
        val exception = Exception()
        Mockito.`when`(remoteDataSource.loadArticles("", "", 1))
            .thenReturn(Result.GeneralError(exception))
        val result = remoteDataSource.loadArticles("", "", 1)
        Mockito.verify(remoteDataSource).loadArticles("", "", 1)
        assert(result is Result.GeneralError)

    }

    @Test
    fun showSuccessResultLoadHeadline() = runBlocking {
        Mockito.`when`(remoteDataSource.loadHeadline("", 1))
            .thenReturn(getSuccessResultData())

        val result = remoteDataSource.loadHeadline("", 1)
        Mockito.verify(remoteDataSource).loadHeadline("", 1)
        assert(result is Result.Success)
        val size = (result as Result.Success).data.size
        Assert.assertEquals(2, size)
    }

    @Test
    fun showResponseErrorLoadHeadline() = runBlocking {
        val failure = Failure("", "Response Error", "400")
        Mockito.`when`(remoteDataSource.loadHeadline("",  1))
            .thenReturn(Result.ResponseError(failure))
        val result = remoteDataSource.loadHeadline("",  1)
        Mockito.verify(remoteDataSource).loadHeadline("",  1)
        assert(result is Result.ResponseError)
    }

    @Test
    fun showGeneralErrorLoadHeadline() = runBlocking {
        val exception = Exception()
        Mockito.`when`(remoteDataSource.loadHeadline("",  1))
            .thenReturn(Result.GeneralError(exception))
        val result = remoteDataSource.loadHeadline("",  1)
        Mockito.verify(remoteDataSource).loadHeadline("",  1)
        assert(result is Result.GeneralError)
    }

}