package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.core.base.MviViewModel
import ru.heatalways.chucknorrisfunfacts.core.models.AppSettings
import ru.heatalways.chucknorrisfunfacts.core.viewmodels.ViewModelFactory
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.domain.interactors.settings.SettingsInteractor
import ru.heatalways.chucknorrisfunfacts.domain.usecases.ClearAppDataBaseUseCase
import javax.inject.Inject

class MainViewModel: MviViewModel<
        MainAction,
        MainViewState,
        MainPartialState
>(MainStateReducer) {
    override val initialState: MainViewState get() = MainViewState()
    override fun handleAction(action: MainAction) = Unit

    class Factory @Inject constructor(): ViewModelFactory<MainViewModel> {
        override fun create(handle: SavedStateHandle) =
            MainViewModel()
    }
}