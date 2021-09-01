package ru.heatalways.chucknorrisfunfacts.domain.utils.paging

sealed class PagingConfig {
    object Initial: PagingConfig()
    data class Update(val itemsCount: Int): PagingConfig()
}
