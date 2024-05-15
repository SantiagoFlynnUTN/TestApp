package com.flynn.core.base.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Store<S : ViewState, I : ViewIntent, A : ReduceAction, E : ViewSideEffect> {

    val state: StateFlow<S>

    val sideEffects: Flow<E>

    fun onIntent(intent: I)
}

fun interface Middleware<S : ViewState, I : ViewIntent, A : ReduceAction, E : ViewSideEffect> {

    suspend fun process(
        intent: I,
        currentState: () -> S,
        dispatchEffect: suspend (E) -> Unit,
        dispatch: suspend (A) -> Unit,
    )
}

fun interface Reducer<S : ViewState, A : ReduceAction> {

    fun reduce(
        currentState: S,
        action: A,
    ): S
}

interface ViewState

interface ViewIntent

interface ReduceAction

interface ViewSideEffect
