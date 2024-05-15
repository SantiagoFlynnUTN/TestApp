package com.flynn.core.base.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

class MviStore<S : ViewState, I : ViewIntent, A : ReduceAction, E : ViewSideEffect>(
    initialState: S,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob()),
    private val reducer: Reducer<S, A> = Reducer { currentState, _ -> currentState },
    private val middleware: Middleware<S, I, A, E>? = null,
) : Store<S, I, A, E> {
    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<S> = _state.asStateFlow()

    private val _sideEffects: Channel<E> = Channel()
    override val sideEffects: Flow<E> = _sideEffects.receiveAsFlow()

    private val intents = MutableSharedFlow<I>(extraBufferCapacity = 64)
    private val actions = MutableSharedFlow<A>(extraBufferCapacity = 64)
    private val effects = MutableSharedFlow<E>(extraBufferCapacity = 64)

    init {
        intents
            .onEach { intent ->
                middleware?.let{
                    scope.launch {
                        it.process(
                            intent,
                            _state::value,
                            effects::emit,
                            actions::emit
                        )
                    }
                }
            }
            .launchIn(scope)

        effects.onEach { effect ->
            sendSideEffect { effect }
        }.launchIn(scope)

        actions
            .scan(initialState) { currentState, action ->
                reducer.reduce(currentState, action)
            }
            .onEach { newState ->
                // discard new state if it is the same as the current state
                if (newState != _state.value) {
                    _state.value = newState
                }
            }
            .launchIn(scope)
    }

    override fun onIntent(intent: I) {
        intents.tryEmit(intent)
    }

    private fun sendSideEffect(builder: () -> E) {
        val effectValue = builder()
        scope.launch { _sideEffects.send(effectValue) }
    }
}
