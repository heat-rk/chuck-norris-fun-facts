package ru.heatalways.chucknorrisfunfacts.module_injector.di

interface ComponentHolder<C : BaseModuleApi, D : BaseModuleDependencies> {
    fun init(dependencies: D)

    fun get(): C

    fun reset()
}

interface BaseModuleDependencies

interface BaseModuleApi