package ru.heatalways.chucknorrisfunfacts.presentation.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<Action: MviAction, State: MviState, Effect: MviEffect>: BaseViewModel() {
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
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _state.value = newState
    }

    /**
     * Set new Effect
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    /**
     * Using [state] while executing reducer from previous (before [state]) state
     */
    protected inline fun stateWhile(state: State, reduce: State.() -> State) {
        val rememberedState = currentState
        setState { state }

        val newState = rememberedState.reduce()
        setState { newState }
    }

    /**
     * Start listening to Action
     */
    private fun subscribeActions() {
        viewModelScope.launch {
            action.collect {
                handleAction(it)
            }
        }
    }

    /**
     * Handle each event
     */
    protected abstract fun handleAction(action : Action)
}