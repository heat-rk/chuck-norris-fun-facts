package ru.heatalways.chucknorrisfunfacts.presentation.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<
        Action: MviAction,
        State: MviState,
        Effect: MviEffect,
        PartialState
>(private val reducer: MviReducer<State, PartialState>): BaseViewModel() {

    // Create Initial State of View
    private val _initialState : State by lazy { initialState }
    abstract val initialState : State

    private val _state : MutableStateFlow<State> = MutableStateFlow(_initialState)
    val state = _state.asStateFlow()

    private val _action : MutableSharedFlow<Action> = MutableSharedFlow()
    val action = _action.asSharedFlow()

    private val _effect : Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    protected val currentState: State get() = state.value

    init {
        subscribeActions()
    }

    /**
     * Set new Action
     */
    fun setAction(event : Action) {
        viewModelScope.launch { _action.emit(event) }
    }

    /**
     * Set new Ui State
     */
    protected fun setState(state: State) {
        _state.value = state
    }

    protected fun reduceState(partialState: PartialState) {
        setState(reducer.reduce(currentState, partialState))
    }

    /**
     * Set new Effect
     */
    protected fun setEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    /**
     * Start listening to Action
     */
    private fun subscribeActions() {
        action.onEach { handleAction(it) }
            .launchIn(viewModelScope)
    }

    /**
     * Handle each event
     */
    protected abstract fun handleAction(action : Action)
}