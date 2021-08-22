package ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes

sealed class Category {
    object Any: Category()
    class Specific(val name: String): Category()
}