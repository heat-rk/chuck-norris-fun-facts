package ru.heatalways.chucknorrisfunfacts.common.general.utils.paging

sealed class PagingConfig {
    object Initial: PagingConfig()
    data class Update(val itemsCount: Int): PagingConfig()
}
