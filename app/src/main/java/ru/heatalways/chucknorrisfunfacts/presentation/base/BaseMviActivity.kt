package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseMviActivity<
        Binding: ViewBinding,
        Event: MviAction,
        State: MviState,
        Effect: MviEffect
>: BaseActivity<Binding>() {


    protected abstract val viewModel: BaseMviViewModel<Event, State, Effect, *>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.state
                .onEach { renderState(it) }
                .launchIn(this)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.effect
                .onEach { handleEffect(it) }
                .launchIn(this)
        }

        if (savedInstanceState == null)
            viewModel.onFirstViewAttach()
    }

    abstract fun renderState(state: State)
    abstract fun handleEffect(effect: Effect)

    protected fun action(event: Event) {
        viewModel.setAction(event)
    }
}