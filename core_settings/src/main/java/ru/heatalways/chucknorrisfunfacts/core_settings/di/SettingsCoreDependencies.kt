package ru.heatalways.chucknorrisfunfacts.core_settings.di

import android.content.Context
import ru.heatalways.chucknorrisfunfacts.module_injector.di.BaseModuleDependencies

interface SettingsCoreDependencies: BaseModuleDependencies {
    fun getApplicationContext(): Context
}