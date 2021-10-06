package ru.heatalways.chucknorrisfunfacts.extensions

import android.os.Bundle
import ru.heatalways.chucknorrisfunfacts.presentation.util.TrackedReference

fun <T: Any> Bundle.getTrackedReference(key: String) =
    getParcelable<TrackedReference<T>>(key)

fun <T: Any> Bundle.putTrackedReference(key: String, value: T) {
    putParcelable(key, TrackedReference(value))
}

fun <T: Any> bundleWithTrackedReference(key: String, value: T) =
    Bundle().apply { putTrackedReference(key, value) }