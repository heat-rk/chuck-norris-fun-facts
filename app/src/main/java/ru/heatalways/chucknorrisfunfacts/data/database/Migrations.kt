package ru.heatalways.chucknorrisfunfacts.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.heatalways.chucknorrisfunfacts.data.database.saved_jokes.ChuckJokeEntity

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE db_chuck_jokes_new (categories TEXT, created_at INTEGER, updated_at INTEGER, icon_url TEXT, id TEXT NOT NULL PRIMARY KEY, url TEXT, value TEXT, saved_at INTEGER)"
        )

        database.execSQL(
            "INSERT INTO db_chuck_jokes_new (categories, created_at, updated_at, icon_url, id, url, value, saved_at) SELECT categories, created_at, updated_at, icon_url, id, url, value, saved_at FROM ${ChuckJokeEntity.TABLE_NAME}"
        )

        database.execSQL(
            "DROP TABLE db_chuck_jokes"
        )

        database.execSQL(
            "ALTER TABLE db_chuck_jokes_new RENAME TO ${ChuckJokeEntity.TABLE_NAME}"
        )
    }
}