package ru.heatalways.chucknorrisfunfacts.extensions

import dagger.hilt.EntryPoints
import ru.heatalways.chucknorrisfunfacts.App

fun <T> entryPointOf(entryPoint: Class<T>) =
    EntryPoints.get(App.instance.applicationContext, entryPoint)
