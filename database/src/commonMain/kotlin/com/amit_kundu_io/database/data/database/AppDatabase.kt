/**
 * AppDatabase.kt
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

package com.amit_kundu_io.database.data.database


import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.amit_kundu_io.database.data.database.dao.AnalyticsDao
import com.amit_kundu_io.database.data.database.dao.BudgetDao
import com.amit_kundu_io.database.data.database.dao.TransactionDao
import com.amit_kundu_io.database.data.database.entity.BudgetEntity
import com.amit_kundu_io.database.data.database.entity.TransactionEntity


@Database(
    entities = [
        TransactionEntity::class,
        BudgetEntity::class
    ],
    version = 3,
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun analyticsDao(): AnalyticsDao
}
// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
