package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import android.view.LayoutInflater
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.ActivityMainBinding
import ru.heatalways.chucknorrisfunfacts.extensions.setVisibleOrGone
import ru.heatalways.chucknorrisfunfacts.extensions.showSmoothly
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviActivity
import ru.ldralighieri.corbind.material.itemSelections

@AndroidEntryPoint
class MainActivity: BaseMviActivity<
        ActivityMainBinding, MainAction,
        MainViewState
>() {
    override val viewModel: MainViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun getFragmentContainerId() = R.id.fragmentContainer

    override val actions get() =
        merge(
            binding.bottomNavigationBar.itemSelections()
                .drop(1)
                .map { MainAction.OnBottomItemChange(it.itemId) }
        )

    override fun renderState(state: MainViewState) = Unit
}