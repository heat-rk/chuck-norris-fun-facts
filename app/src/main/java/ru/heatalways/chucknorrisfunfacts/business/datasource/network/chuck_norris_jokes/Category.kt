package ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes

sealed class Category {
    object Any: Category()
    class Specific(val name: String): Category()
}