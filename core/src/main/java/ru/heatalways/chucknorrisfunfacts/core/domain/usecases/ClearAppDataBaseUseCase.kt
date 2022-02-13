package ru.heatalways.chucknorrisfunfacts.core.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.chucknorrisfunfacts.common.di.IoDispatcher
import ru.heatalways.chucknorrisfunfacts.core.data.database.AppDatabase
import javax.inject.Inject

class ClearAppDataBaseUseCase @Inject constructor(
    private val appDatabase: AppDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun execute() = withContext(dispatcher) {
        appDatabase.clearAllTables()
    }
}