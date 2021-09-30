package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseMviActivity<
        Binding: ViewBinding,
        Action: MviAction,
        State: MviState
>: BaseActivity<Binding>() {
    protected abstract val viewModel: BaseMviViewModel<Action, State, *>

    protected abstract val actions: Flow<Action>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.state
                .onEach { renderState(it) }
                .launchIn(this)

            actions
                .onEach { viewModel.setAction(it) }
                .launchIn(this)
        }
    }

    abstract fun renderState(state: State)
}