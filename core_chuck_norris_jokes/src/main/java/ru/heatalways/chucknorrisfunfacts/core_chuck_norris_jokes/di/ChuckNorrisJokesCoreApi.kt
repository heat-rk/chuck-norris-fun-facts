package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di

import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.module_injector.di.BaseModuleApi

interface ChuckNorrisJokesCoreApi: BaseModuleApi {
    fun getChuckNorrisJokesRepository(): ChuckNorrisJokesRepository
    fun getChuckNorrisJokesInteractor(): ChuckNorrisJokesInteractor
}