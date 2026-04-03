/**
 * MIGRATION_2_3.kt
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

val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(connection: SQLiteConnection) {

        //  Create new table with correct schema
        connection.execSQL("""
            CREATE TABLE IF NOT EXISTS transactions_new (
                id TEXT NOT NULL PRIMARY KEY,
                title TEXT NOT NULL,
                amount REAL NOT NULL,
                transactionType INTEGER NOT NULL,
                category INTEGER,
                paymentMethod INTEGER NOT NULL,
                note TEXT,
                date INTEGER NOT NULL
            )
        """.trimIndent())

        //  Copy data (map old 'type' → new 'transactionType')
        connection.execSQL("""
            INSERT INTO transactions_new (
                id, title, amount, transactionType, category, paymentMethod, note, date
            )
            SELECT 
                id, title, amount, type, category, paymentMethod, note, date
            FROM transactions
        """.trimIndent())

        // Delete old table
        connection.execSQL("DROP TABLE transactions")

        // Rename new table
        connection.execSQL("ALTER TABLE transactions_new RENAME TO transactions")

        //  Recreate indices (VERY IMPORTANT)
        connection.execSQL("CREATE INDEX IF NOT EXISTS index_transactions_date ON transactions(date)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS index_transactions_category ON transactions(category)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS index_transactions_transactionType ON transactions(transactionType)")
    }
}