/**
 * BudgetDao.kt
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

package com.amit_kundu_io.database.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amit_kundu_io.database.data.database.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: BudgetEntity)

    @Query("""
        SELECT * FROM budgets 
        WHERE month = :month AND year = :year
        LIMIT 1
    """)
    fun getBudget(month: Int, year: Int): Flow<BudgetEntity?>

    @Query("""
        DELETE FROM budgets 
        WHERE month = :month AND year = :year
    """)
    suspend fun deleteBudget(month: Int, year: Int)
}