package ru.heatalways.chucknorrisfunfacts.core.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.*

abstract class MviFragment<
        Action: MviAction,
        State: MviState
>(
    @LayoutRes contentLayoutId: Int
): Fragment(contentLayoutId) {

    protected abstract val viewModel: MviViewModel<Action, State, *>

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

            viewModel.navigation
                .onEach { action -> findNavController().action() }
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