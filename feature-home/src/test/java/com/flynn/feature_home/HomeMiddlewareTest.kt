package com.flynn.feature_home

import com.flynn.domain.resource.Resource.Failed
import com.flynn.domain.resource.Resource.Loading
import com.flynn.domain.resource.Resource.Success
import com.flynn.domain.usecase.ClearLocalDataUseCase
import com.flynn.domain.usecase.GetAboutPageContentUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class HomeMiddlewareTest {

    private lateinit var currentState: HomeState
    private lateinit var getAboutPageContentUseCase: GetAboutPageContentUseCase
    private lateinit var clearLocalDataUseCase: ClearLocalDataUseCase

    private lateinit var middleware: HomeMiddleware

    @Before
    fun setUp() {
        currentState = HomeState()
        getAboutPageContentUseCase = mock()
        clearLocalDataUseCase = mock()

        middleware = HomeMiddleware(
            getAboutPageContentUseCase = getAboutPageContentUseCase,
            clearLocalDataUseCase = clearLocalDataUseCase
        )
    }

    @Test
    fun `when process ProcessContent intent getAboutPageContentUseCase success`() = runTest {
        val intent = HomeIntent.ProcessContent
        val content = "Test"

        val actionHistory = mutableListOf<HomeReduceAction>()

        given(getAboutPageContentUseCase()).willReturn(flowOf(Loading, Success(content)))

        middleware.process(
            intent = intent,
            currentState = { currentState },
            dispatchEffect = { },
            dispatch = { actionHistory.add(it) }
        )

        assertTrue(actionHistory[0] is HomeReduceAction.ShowLoadingTop)
        assertTrue(actionHistory[1] is HomeReduceAction.SetConvertedContent)
        assertTrue(actionHistory[2] is HomeReduceAction.ShowLoadingTop)
    }

    @Test
    fun `when process ProcessContent intent getAboutPageContentUseCase failure`() = runTest {
        val intent = HomeIntent.ProcessContent
        val error = Exception("Error")

        val actionHistory = mutableListOf<HomeReduceAction>()
        val sideEffectHistory = mutableListOf<HomeSideEffect>()

        given(getAboutPageContentUseCase()).willReturn(flowOf(Loading, Failed(Error(error))))

        middleware.process(
            intent = intent,
            currentState = { currentState },
            dispatchEffect = { sideEffectHistory.add(it) },
            dispatch = { actionHistory.add(it) }
        )

        assertTrue(actionHistory[0] is HomeReduceAction.ShowLoadingTop)
        assertTrue(sideEffectHistory[0] is HomeSideEffect.ShowToast)
        assertTrue(actionHistory[1] is HomeReduceAction.ShowLoadingTop)
    }

    @Test
    fun `when process CountWords intent getAboutPageContentUseCase success`() = runTest {
        val intent = HomeIntent.CountWords
        val content = "test"

        val actionHistory = mutableListOf<HomeReduceAction>()

        given(getAboutPageContentUseCase()).willReturn(flowOf(Loading, Success(content)))

        middleware.process(
            intent = intent,
            currentState = { currentState },
            dispatchEffect = { },
            dispatch = { actionHistory.add(it) }
        )

        assertTrue(actionHistory[0] is HomeReduceAction.ShowLoadingBottom)
        assertTrue(actionHistory[1] is HomeReduceAction.SetWordsCounted)
        assertTrue(actionHistory[2] is HomeReduceAction.ShowLoadingBottom)
    }

    @Test
    fun `when process CountWords intent getAboutPageContentUseCase failure`() = runTest {
        val intent = HomeIntent.CountWords
        val error = Exception("Error")

        val actionHistory = mutableListOf<HomeReduceAction>()
        val sideEffectHistory = mutableListOf<HomeSideEffect>()

        given(getAboutPageContentUseCase()).willReturn(flowOf(Loading, Failed(Error(error))))

        middleware.process(
            intent = intent,
            currentState = { currentState },
            dispatchEffect = { sideEffectHistory.add(it) },
            dispatch = { actionHistory.add(it) }
        )

        assertTrue(actionHistory[0] is HomeReduceAction.ShowLoadingBottom)
        assertTrue(sideEffectHistory[0] is HomeSideEffect.ShowToast)
        assertTrue(actionHistory[1] is HomeReduceAction.ShowLoadingBottom)
    }

    @Test
    fun `when process ClearDb intent then dispatch ClearProcessedContent and ShowToast`() = runTest {
        val intent = HomeIntent.ClearDb
        val actionHistory = mutableListOf<HomeReduceAction>()
        val sideEffectHistory = mutableListOf<HomeSideEffect>()

        middleware.process(
            intent = intent,
            currentState = { currentState },
            dispatchEffect = { sideEffectHistory.add(it) },
            dispatch = { actionHistory.add(it) }
        )

        assertTrue(actionHistory[0] is HomeReduceAction.ClearProcessedContent)
        assertTrue(sideEffectHistory[0] is HomeSideEffect.ShowToast)
    }
}