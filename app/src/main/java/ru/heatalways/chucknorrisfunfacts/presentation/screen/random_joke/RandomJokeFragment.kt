package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.FragmentRandomJokeBinding
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseFragment

class RandomJokeFragment: BaseFragment<FragmentRandomJokeBinding>() {
    private val randomJokeViewModel: RandomJokeViewModel by viewModels()

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRandomJokeBinding {
        return FragmentRandomJokeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun getScreen() = FragmentScreen { RandomJokeFragment() }
    }
}