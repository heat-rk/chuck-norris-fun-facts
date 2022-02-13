package ru.heatalways.chucknorrisfunfacts.random_joke.di

import ru.heatalways.chucknorrisfunfacts.module_injector.di.BaseModuleDependencies
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor

interface RandomJokeFeatureDependencies: BaseModuleDependencies {
    fun getChuckNorrisJokesInteractor(): ChuckNorrisJokesInteractor
}