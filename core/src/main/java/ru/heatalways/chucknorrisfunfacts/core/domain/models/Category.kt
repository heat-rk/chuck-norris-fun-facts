package ru.heatalways.chucknorrisfunfacts.core.domain.models

import java.io.Serializable

sealed class Category: Serializable {
    object Any: Category()
    data class Specific(val name: String): Category()
}