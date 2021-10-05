package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.ActivityMainBinding
import ru.heatalways.chucknorrisfunfacts.presentation.base.BindingMviActivity
import ru.ldralighieri.corbind.material.itemSelections

@AndroidEntryPoint
class MainActivity: BindingMviActivity<
        ActivityMainBinding, MainAction,
        MainViewState
>(
    bindingInflater = ActivityMainBinding::inflate,
    fragmentContainerId = R.id.fragmentContainer
) {
    override val viewModel: MainViewModel by viewModels()

    override val actions get() =
        merge(
            binding.bottomNavigationBar.itemSelections()
                .drop(1)
                .map { MainAction.OnBottomItemChange(it.itemId) }
        )

    override fun renderState(state: MainViewState) = Unit
}