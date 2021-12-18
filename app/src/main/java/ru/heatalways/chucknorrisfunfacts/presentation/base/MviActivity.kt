package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class MviActivity<
        Action: MviAction,
        State: MviState
>: AppCompatActivity() {
    protected abstract val viewModel: MviViewModel<Action, State, *>

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