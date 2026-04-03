package com.amit_kundu_io.database.data.database

import amitkundu.database.DB_KEY
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amit_kundu_io.database.data.database.di.MIGRATION_1_2


actual class DatabaseBuilderFactory(
    private val context: Context
) {
    actual fun createBuilder(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(DB_KEY.DB_NAME)
        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        ).addMigrations(MIGRATION_1_2)
    }
}