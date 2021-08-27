package ru.heatalways.chucknorrisfunfacts.domain.interactors.main

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect

sealed class MainViewEffect: MviEffect {
    data class SelectFragment(val screen: FragmentScreen): MainViewEffect()
}