package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseMviActivity<
        Binding: ViewBinding,
        Event: MviAction,
        State: MviState
>: BaseActivity<Binding>() {


    protected abstract val viewModel: BaseMviViewModel<Event, State, *>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.state
                .onEach { renderState(it) }
                .launchIn(this)
        }
    }

    abstract fun renderState(state: State)

    protected fun action(event: Event) {
        viewModel.setAction(event)
    }
}