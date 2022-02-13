package ru.heatalways.chucknorrisfunfacts.core.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MviViewModel<
        Action: MviAction,
        State: MviState,
        PartialState
>(private val reducer: MviReducer<State, PartialState>): ViewModel() {

    // Create Initial State of View
    private val _initialState : State by lazy { initialState }
    abstract val initialState : State

    private val _state : MutableStateFlow<State> = MutableStateFlow(_initialState)
    val state = _state.asStateFlow()

    private val _action : MutableSharedFlow<Action> = MutableSharedFlow()
    val action = _action.asSharedFlow()

    private val _navigation = Channel<NavController.() -> Unit>()
    val navigation = _navigation.receiveAsFlow()

    private val currentState: State get() = state.value

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

    fun resetState() {
        _state.value = _initialState
    }

    protected fun navigator(action: NavController.() -> Unit) {
        viewModelScope.launch { _navigation.send(action) }
    }
}