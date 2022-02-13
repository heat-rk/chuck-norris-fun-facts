package ru.heatalways.chucknorrisfunfacts.settings.presentation.screen.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviViewModel
import ru.heatalways.chucknorrisfunfacts.core_settings.domain.interactors.settings.SettingsInteractor
import ru.heatalways.chucknorrisfunfacts.common.presentation.factories.ViewModelFactory
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.AppUtils
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.CompoundButtonState
import javax.inject.Inject

internal class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor
): MviViewModel<
        SettingsAction,
        SettingsViewState,
        SettingsPartialState
        >(SettingsStateReducer) {

    override val initialState get() = SettingsViewState(isLoading = true)

    init {
        observeSettings()
    }

    private fun observeSettings() {
        var isInitial = true

        settingsInteractor.settings
            .onEach { settings ->
                reduceState(SettingsPartialState.ClearCacheAfterExitSwitchState(
                    state = CompoundButtonState(
                        isChecked = settings.isClearCacheAfterExitEnabled,
                        animate = !isInitial
                    )
                ))

                reduceState(SettingsPartialState.NightModeSwitchState(
                    state = CompoundButtonState(
                        isChecked = settings.isNightModeEnabled,
                        animate = !isInitial
                    )
                ))

                AppUtils.setDefaultNightMode(settings.isNightModeEnabled)

                isInitial = false
            }
            .launchIn(viewModelScope)
    }

    override fun handleAction(action: SettingsAction) {
        viewModelScope.launch {
            when (action) {
                is SettingsAction.OnClearCacheAfterExitCheckedChange ->
                    settingsInteractor.saveClearCacheAfterExitEnabled(action.isChecked)

                is SettingsAction.OnNightModeCheckedChange ->
                    settingsInteractor.saveNightModeEnabled(action.isChecked)
            }
        }
    }

    class Factory @Inject constructor(
        private val settingsInteractor: SettingsInteractor
    ): ViewModelFactory<SettingsViewModel> {
        override fun create(handle: SavedStateHandle) =
            SettingsViewModel(settingsInteractor)
    }
}