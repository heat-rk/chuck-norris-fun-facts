package ru.heatalways.chucknorrisfunfacts.settings.presentation.screen.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.heatalways.chucknorrisfunfacts.common.extensions.checkedChangesIntents
import ru.heatalways.chucknorrisfunfacts.common.extensions.setChecked
import ru.heatalways.chucknorrisfunfacts.common.presentation.factories.GenericSavedStateViewModelFactory
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.CompoundButtonState
import ru.heatalways.chucknorrisfunfacts.core.databinding.FragmentSettingsBinding
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviFragment
import ru.heatalways.chucknorrisfunfacts.settings.R
import ru.heatalways.chucknorrisfunfacts.settings.di.SettingsFeatureComponentHolder
import javax.inject.Inject

internal class SettingsFragment: MviFragment<
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SettingsFeatureComponentHolder.getComponent().inject(this)
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