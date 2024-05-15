package com.flynn.feature_home

import com.flynn.core.base.mvi.ReduceAction
import com.flynn.core.base.mvi.ViewIntent
import com.flynn.core.base.mvi.ViewSideEffect
import com.flynn.core.base.mvi.ViewState

data class HomeState(
    val text: String = "",
    val convertedContent: String = "",
    val contentWordsCounted: Map<String, Int> = emptyMap(),
    val loadingTopBox: Boolean = false,
    val loadingBottomBox: Boolean = false
): ViewState

sealed class HomeIntent : ViewIntent {
    data object ProcessContent : HomeIntent()
    data object CountWords : HomeIntent()
    data object ClearDb : HomeIntent()
}

sealed class HomeSideEffect : ViewSideEffect {
    data class ShowToast(val text: String) : HomeSideEffect()
}

sealed class HomeReduceAction : ReduceAction {
    data class ShowLoadingTop(val show: Boolean) : HomeReduceAction()
    data class ShowLoadingBottom(val show: Boolean) : HomeReduceAction()
    data class SetConvertedContent(val content: String) : HomeReduceAction()
    data class SetWordsCounted(val contentWordsCounted: Map<String, Int>) : HomeReduceAction()
    data object ClearProcessedContent : HomeReduceAction()
}
