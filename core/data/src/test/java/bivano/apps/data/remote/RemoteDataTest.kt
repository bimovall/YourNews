package bivano.apps.data.remote

import bivano.apps.data.getErrorRequestData
import bivano.apps.data.getErrorResponseData
import bivano.apps.data.getSuccessResponseData
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class RemoteDataTest {

    @Mock
    lateinit var newsService: NewsService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun showSuccessResultLoadArticle() = runBlocking {
        Mockito.`when`(newsService.getArticle(anyString(), anyString(), anyString(), anyInt(), anyInt()))
            .thenReturn(getSuccessResponseData())
        val result = newsService.getArticle(anyString(), anyString(), anyString(), anyInt(), anyInt())
        Mockito.verify(newsService).getArticle(anyString(), anyString(), anyString(), anyInt(), anyInt())
        assert(result.isSuccessful)
        assert(result.body() != null)
        assert(result.body()?.code == null)
    }

    @Test
    fun showResponseErrorLoadArticle() = runBlocking {
        Mockito.`when`(newsService.getArticle(anyString(), anyString(), anyString(), anyInt(), anyInt()))
            .thenReturn(getErrorResponseData())
        val result = newsService.getArticle(anyString(), anyString(), anyString(), anyInt(), anyInt())
        Mockito.verify(newsService).getArticle(anyString(), anyString(), anyString(), anyInt(), anyInt())
        assert(result.isSuccessful)
        assert(result.body()?.status.equals("error"))
    }

    @Test
    fun showGeneralErrorLoadArticle() = runBlocking {
        Mockito.`when`(newsService.getArticle(anyString(), anyString(), anyString(), anyInt(), anyInt()))
            .thenReturn(getErrorRequestData())
        val result = newsService.getArticle(anyString(), anyString(), anyString(), anyInt(), anyInt())
        Mockito.verify(newsService).getArticle(anyString(), anyString(), anyString(), anyInt(), anyInt())
        assert(!result.isSuccessful)
        assert(result.errorBody() != null)
    }

    @Test
    fun showSuccessResultLoadHeadline() = runBlocking {
        Mockito.`when`(newsService.getHeadlines(anyString(), anyString(), anyInt(), anyInt()))
            .thenReturn(getSuccessResponseData())
        val result = newsService.getHeadlines(anyString(), anyString(), anyInt(), anyInt())
        Mockito.verify(newsService).getHeadlines(anyString(), anyString(), anyInt(), anyInt())
        assert(result.isSuccessful)
        assert(result.body() != null)
        assert(result.body()?.code == null)
    }

    @Test
    fun showResponseErrorLoadHeadline() = runBlocking {
        Mockito.`when`(newsService.getHeadlines(anyString(), anyString(), anyInt(), anyInt()))
            .thenReturn(getErrorResponseData())
        val result = newsService.getHeadlines(anyString(), anyString(), anyInt(), anyInt())
        Mockito.verify(newsService).getHeadlines(anyString(), anyString(), anyInt(), anyInt())
        assert(result.isSuccessful)
        assert(result.body()?.status.equals("error"))
    }

    @Test
    fun showGeneralErrorLoadHeadline() = runBlocking {
        Mockito.`when`(newsService.getHeadlines(anyString(), anyString(), anyInt(), anyInt()))
            .thenReturn(getErrorRequestData())
        val result = newsService.getHeadlines(anyString(), anyString(), anyInt(), anyInt())
        Mockito.verify(newsService).getHeadlines(anyString(), anyString(), anyInt(), anyInt())
        assert(!result.isSuccessful)
        assert(result.errorBody() != null)
    }

}