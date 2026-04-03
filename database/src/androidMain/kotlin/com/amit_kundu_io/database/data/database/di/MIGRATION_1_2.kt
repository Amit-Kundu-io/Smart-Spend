/**
 * MIGRATION_1_2.kt
 *
 * Author      : Amit Kundu
 * Created On  : 03/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.database.data.database.di

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(connection: SQLiteConnection) {

        connection.execSQL("""
            CREATE TABLE IF NOT EXISTS budgets (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                amount REAL NOT NULL,
                month INTEGER NOT NULL,
                year INTEGER NOT NULL
            )
        """.trimIndent())

        connection.execSQL("""
            CREATE UNIQUE INDEX IF NOT EXISTS 
            index_budgets_month_year 
            ON budgets (month, year)
        """.trimIndent())
    }
}