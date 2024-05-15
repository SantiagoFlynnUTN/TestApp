package com.flynn.feature_home

import com.flynn.core.base.mvi.Middleware
import com.flynn.domain.usecase.ClearLocalDataUseCase
import com.flynn.domain.usecase.GetAboutPageContentUseCase
import com.flynn.domain.usecase.executeUseCase
import com.flynn.feature_home.utils.countWords
import com.flynn.feature_home.utils.getEveryNthChar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeMiddleware(
    private val getAboutPageContentUseCase: GetAboutPageContentUseCase,
    private val clearLocalDataUseCase: ClearLocalDataUseCase
) : Middleware<HomeState, HomeIntent, HomeReduceAction, HomeSideEffect> {
    override suspend fun process(
        intent: HomeIntent,
        currentState: () -> HomeState,
        dispatchEffect: suspend (HomeSideEffect) -> Unit,
        dispatch: suspend (HomeReduceAction) -> Unit,
    ) {
        when (intent) {
            HomeIntent.ProcessContent -> {
                executeUseCase(
                    useCase = getAboutPageContentUseCase(),
                    onLoading = {
                        dispatch(HomeReduceAction.ShowLoadingTop(true))
                    },
                    onSuccess = { data ->
                        // the task of processing the data could potentially be very long running
                        // so its better to do it on the cpu dispatcher, outside the ui thread
                        val dataProcessed = withContext(Dispatchers.Default) {
                            data.getEveryNthChar(10)
                        }
                        dispatch(HomeReduceAction.SetConvertedContent(dataProcessed))
                        dispatch(HomeReduceAction.ShowLoadingTop(false))
                    },
                    onFailure = { error ->
                        dispatchEffect(HomeSideEffect.ShowToast(error.toString()))
                        dispatch(HomeReduceAction.ShowLoadingTop(false))
                    }
                )
            }

            HomeIntent.CountWords -> {
                executeUseCase(
                    useCase = getAboutPageContentUseCase(),
                    onLoading = {
                        dispatch(HomeReduceAction.ShowLoadingBottom(true))
                    },
                    onSuccess = { data ->
                        // the task of processing the data could potentially be very long running
                        // so its better to do it on the cpu dispatcher, outside the ui thread
                        val dataProcessed = withContext(Dispatchers.Default) {data.countWords()}
                        dispatch(HomeReduceAction.SetWordsCounted(dataProcessed))
                        dispatch(HomeReduceAction.ShowLoadingBottom(false))
                    },
                    onFailure = { error ->
                        dispatchEffect(HomeSideEffect.ShowToast(error.toString()))
                        dispatch(HomeReduceAction.ShowLoadingBottom(false))
                    }
                )
            }

            HomeIntent.ClearDb -> {
                clearLocalDataUseCase()
                dispatch(HomeReduceAction.ClearProcessedContent)
                dispatchEffect(HomeSideEffect.ShowToast("Db Cleared"))
            }
        }
    }
}
