package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseMviFragment<
        Binding: ViewBinding,
        Event: MviAction,
        State: MviState
>: BaseFragment<Binding>() {

    protected abstract val viewModel: BaseMviViewModel<Event, State, *>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
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