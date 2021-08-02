package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding

abstract class BaseMviFragment<
        Binding: ViewBinding,
        Event: MviAction,
        State: MviState,
        Effect: MviEffect
>: BaseFragment<Binding>() {

    abstract val viewModel: BaseMviViewModel<Event, State, Effect>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collect(viewModel.state) { renderState(it) }
        collect(viewModel.effect) { handleEffect(it) }
    }

    abstract fun renderState(state: State)
    abstract fun handleEffect(effect: Effect)

    protected fun action(event: Event) {
        viewModel.setAction(event)
    }
}