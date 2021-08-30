package ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes

import java.io.Serializable

sealed class Category: Serializable {
    object Any: Category()
    data class Specific(val name: String): Category()
}