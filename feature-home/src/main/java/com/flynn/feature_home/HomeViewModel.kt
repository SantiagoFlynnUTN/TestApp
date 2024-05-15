package com.flynn.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flynn.core.base.mvi.MviStore
import com.flynn.core.base.mvi.Store
import com.flynn.domain.usecase.ClearLocalDataUseCase
import com.flynn.domain.usecase.GetAboutPageContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAboutPageContentUseCase: GetAboutPageContentUseCase,
    clearLocalDataUseCase: ClearLocalDataUseCase
) : ViewModel(), Store<
        HomeState, HomeIntent, HomeReduceAction, HomeSideEffect> {
    private val scope = viewModelScope
    private val store: Store<HomeState, HomeIntent, HomeReduceAction, HomeSideEffect> =
        MviStore(
            initialState = HomeState(),
            reducer = HomeReducer(),
            middleware = HomeMiddleware(
                getAboutPageContentUseCase = getAboutPageContentUseCase,
                clearLocalDataUseCase = clearLocalDataUseCase
            ),
            scope = scope
        )

    override val state: StateFlow<HomeState> = store.state
    override val sideEffects: Flow<HomeSideEffect> = store.sideEffects

    override fun onIntent(intent: HomeIntent) {
        store.onIntent(intent)
    }
}
