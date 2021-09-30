package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseMviFragment<
        Binding: ViewBinding,
        Action: MviAction,
        State: MviState
>: BaseFragment<Binding>() {

    protected abstract val viewModel: BaseMviViewModel<Action, State, *>

    protected var previousState: State? = null

    protected abstract val actions: Flow<Action>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state
                .onEach {
                    renderState(it)
                    previousState = it
                }
                .launchIn(this)

            actions
                .onEach { viewModel.setAction(it) }
                .launchIn(this)
        }
    }

    abstract fun renderState(state: State)

    override fun onDestroyView() {
        previousState = null
        super.onDestroyView()
    }
}