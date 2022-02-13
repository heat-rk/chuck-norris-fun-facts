package ru.heatalways.chucknorrisfunfacts.core.di

import android.content.Context
import ru.heatalways.chucknorrisfunfacts.module_injector.di.BaseModuleDependencies

interface CoreDependencies: BaseModuleDependencies {
    fun getApplicationContext(): Context
}