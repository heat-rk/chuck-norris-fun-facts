package ru.heatalways.chucknorrisfunfacts.extensions

fun <T> List<T>.editLast(reducer: T.() -> T) =
    toMutableList().apply {
        lastOrNull()?.let { last ->
            this[this.lastIndex] = last.reducer()
        }
    }

fun <T> T?.singleList() = this?.let { listOf(it) } ?: emptyList()