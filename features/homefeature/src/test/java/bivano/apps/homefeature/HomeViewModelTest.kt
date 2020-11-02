package bivano.apps.homefeature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import bivano.apps.common.Failure
import bivano.apps.common.Result
import bivano.apps.common.model.Article
import bivano.apps.common.test.MainCoroutineRule
import bivano.apps.data.repository.achieved.AchievedRepository
import bivano.apps.data.repository.headline.HeadlineRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val instantTestRules = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var headlineRepository: HeadlineRepository

    @Mock
    private lateinit var achievedRepository: AchievedRepository

    @Mock
    private lateinit var stateObserver: Observer<Result<List<Article>>>


    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeViewModel(headlineRepository, achievedRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load data and return result data`() {
        homeViewModel.loadData("abc")
        mainCoroutineRule.runBlocking {
            val expected = flow {
                emit(getSuccessResultData())
            }
            Mockito.`when`(headlineRepository.loadHeadline("abc")).thenReturn(expected)
            homeViewModel.stateNetworkData.observeForever(stateObserver)

            try {
                Mockito.verify(headlineRepository, times(1)).loadHeadline("abc")
                Mockito.verify(stateObserver).onChanged(getSuccessResultData())
                val result = homeViewModel.stateNetworkData.value
                if (result is Result.Success) {
                    Assert.assertTrue(result.data.size == 2)
                }
            } finally {
                homeViewModel.stateNetworkData.removeObserver(stateObserver)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load data and return loading state`() {
        homeViewModel.loadData("abc")
        mainCoroutineRule.runBlocking {
            val expected = flow {
                emit(Result.Loading)
            }
            Mockito.`when`(headlineRepository.loadHeadline("abc")).thenReturn(expected)
            homeViewModel.stateNetworkData.observeForever(stateObserver)

            try {
                Mockito.verify(headlineRepository, times(1)).loadHeadline("abc")
                Mockito.verify(stateObserver).onChanged(Result.Loading)
            } finally {
                homeViewModel.stateNetworkData.removeObserver(stateObserver)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load data and return error response`() {
        homeViewModel.loadData("abc")
        mainCoroutineRule.runBlocking {
            val error = Failure("500", "Error", "")
            val expected = flow {
                emit(Result.ResponseError(error))
            }
            Mockito.`when`(headlineRepository.loadHeadline("abc")).thenReturn(expected)
            homeViewModel.stateNetworkData.observeForever(stateObserver)

            try {
                Mockito.verify(headlineRepository, times(1)).loadHeadline("abc")
                Mockito.verify(stateObserver).onChanged(Result.ResponseError(error))
            } finally {
                homeViewModel.stateNetworkData.removeObserver(stateObserver)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load data and return general error`() {
        val error = Exception()
        homeViewModel.loadData("abc")
        mainCoroutineRule.runBlocking {
            val expected = flow {
                emit(Result.GeneralError(error))
            }
            Mockito.`when`(headlineRepository.loadHeadline("abc")).thenReturn(expected)
            homeViewModel.stateNetworkData.observeForever(stateObserver)

            try {
                Mockito.verify(headlineRepository, times(1)).loadHeadline("abc")
                Mockito.verify(stateObserver).onChanged(Result.GeneralError(error))
            } finally {
                homeViewModel.stateNetworkData.removeObserver(stateObserver)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `save article and verify`() {
        mainCoroutineRule.runBlocking {
            val article = Article(null, "a", "title", "desc", "url", "", null, "")
            achievedRepository.saveNews(article)

            Mockito.verify(achievedRepository).saveNews(article)

        }
    }

    private fun getSuccessResultData(): Result<List<Article>> {
        val article = listOf(
            Article(null, "a", "title", "desc", "", "", null, ""),
            Article(null, "b", "title2", "desc2", "", "", null, "")
        )
        return Result.Success(article)
    }

}