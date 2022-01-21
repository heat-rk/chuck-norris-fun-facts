package ru.heatalways.chucknorrisfunfacts.presentation.screen.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.appComponent
import ru.heatalways.chucknorrisfunfacts.core.base.MviFragment
import ru.heatalways.chucknorrisfunfacts.core.viewmodels.GenericSavedStateViewModelFactory
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentSettingsBinding
import ru.heatalways.chucknorrisfunfacts.extensions.checkedChangesIntents
import ru.heatalways.chucknorrisfunfacts.extensions.setChecked
import ru.heatalways.chucknorrisfunfacts.presentation.util.CompoundButtonState
import javax.inject.Inject

class SettingsFragment: MviFragment<
        SettingsAction,
        SettingsViewState
>(R.layout.fragment_settings) {

    @Inject
    lateinit var viewModeFactory: SettingsViewModel.Factory

    override val viewModel: SettingsViewModel by viewModels {
        GenericSavedStateViewModelFactory(viewModeFactory, this)
    }

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override val actions get() =
        merge(
            binding.nightModeSwitch.checkedChangesIntents()
                .map { SettingsAction.OnNightModeCheckedChange(it) },

            binding.clearCacheAfterExitSwitch.checkedChangesIntents()
                .map { SettingsAction.OnClearCacheAfterExitCheckedChange(it) },
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appbar.toolbar.setTitle(R.string.settings)
    }

    override fun renderState(state: SettingsViewState) {
        renderNightModeCheckBox(state.nightModeSwitchState)
        renderClearCacheAfterExitCheckBox(state.clearCacheAfterExitSwitchState)
    }

    private fun renderNightModeCheckBox(state: CompoundButtonState) {
        if (previousState?.nightModeSwitchState != state)
            binding.nightModeSwitch.setChecked(state.isChecked, state.animate)
    }

    private fun renderClearCacheAfterExitCheckBox(state: CompoundButtonState) {
        if (previousState?.clearCacheAfterExitSwitchState != state)
            binding.clearCacheAfterExitSwitch.setChecked(state.isChecked, state.animate)
    }
}