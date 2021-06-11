package ru.heatalways.chucknorrisfunfacts.data.entities

sealed class Category {
    object Any: Category()
    class Specific(val name: String): Category()
}