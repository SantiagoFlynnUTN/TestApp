package com.flynn.feature_home

import com.flynn.core.base.mvi.Reducer

class HomeReducer: Reducer<HomeState, HomeReduceAction> {
    override fun reduce(
        currentState: HomeState,
        action: HomeReduceAction
    ): HomeState {
        return when (action) {
            is HomeReduceAction.SetConvertedContent -> {
                currentState.copy(convertedContent = action.content)
            }

            is HomeReduceAction.ShowLoadingTop -> {
                currentState.copy(loadingTopBox = action.show)
            }

            is HomeReduceAction.ShowLoadingBottom -> {
                currentState.copy(loadingBottomBox = action.show)
            }

            is HomeReduceAction.ClearProcessedContent -> {
                currentState.copy(convertedContent = "",
                    contentWordsCounted = emptyMap())
            }
            is HomeReduceAction.SetWordsCounted -> {
                currentState.copy(contentWordsCounted = action.contentWordsCounted)
            }

        }
    }
}