package ru.heatalways.chucknorrisfunfacts.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.di.modules.IoDispatcher
import javax.inject.Inject

class ClearAppDataBaseUseCase @Inject constructor(
    private val appDatabase: AppDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun execute() = withContext(dispatcher) {
        appDatabase.clearAllTables()
    }
}