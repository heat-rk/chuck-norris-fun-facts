package ru.heatalways.chucknorrisfunfacts.common.general.utils.paging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class PagingSource<S, E> {
    private var isPaginationAvailable = true

    abstract val initialPageSize: Int
    abstract val pageSize: Int

    abstract suspend fun load(offset: Int, limit: Int): PagingData<S, E>

    fun execute(pagingConfig: PagingConfig): Flow<PagingEvent<S, E>> = flow {
        when (pagingConfig) {
            is PagingConfig.Initial -> {
                emit(getFirstItems(initialPageSize))
            }

            is PagingConfig.Update -> {
                if (isPaginationAvailable) {
                    emit(PagingEvent.Updating())
                    emit(updateItems(
                        limit = pageSize,
                        offset = pagingConfig.itemsCount
                    ))
                }
            }
        }
    }

    private suspend fun getFirstItems(limit: Int): PagingEvent<S, E> {
        isPaginationAvailable = false

        return when (val data = load(offset = 0, limit = limit)) {
            is PagingData.Error -> PagingEvent.LoadError(data.error)

            is PagingData.Success ->
                if (data.body.isNotEmpty()) {
                    isPaginationAvailable = true
                    PagingEvent.Loaded(data.body)
                } else {
                    isPaginationAvailable = false
                    PagingEvent.Loaded(emptyList())
                }
        }
    }

    private suspend fun updateItems(offset: Int, limit: Int): PagingEvent<S, E> {
        isPaginationAvailable = false

        return when (val data = load(
            limit = limit,
            offset = offset
        )) {
            is PagingData.Error -> PagingEvent.UpdateError(data.error)

            is PagingData.Success ->
                if (data.body.isNotEmpty()) {
                    isPaginationAvailable = true
                    PagingEvent.Updated(data.body)
                } else {
                    isPaginationAvailable = false
                    PagingEvent.Updated(emptyList())
                }
        }
    }
}