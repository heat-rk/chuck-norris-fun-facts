package ru.heatalways.chucknorrisfunfacts.extensions

fun <T> MutableList<T>.editLast(reducer: T.() -> T) =
    apply {
        lastOrNull()?.let { last ->
            this[this.lastIndex] = last.reducer()
        }
    }

fun <T> T?.singleList() = this?.let { listOf(it) } ?: emptyList()