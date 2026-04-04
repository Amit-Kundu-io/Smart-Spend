/**
 * AnalyticsDao.kt
 *
 * Author      : Amit Kundu
 * Created On  : 04/04/2026
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
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalyticsDao {
    @Query(
        """
    SELECT IFNULL(SUM(amount), 0) 
    FROM transactions 
    WHERE transactionType = :type AND date BETWEEN :start AND :end
"""
    )
    fun getTotalByTypeInRange(
        type: Int,
        start: Long,
        end: Long
    ): Flow<Double>

    @Query(
        """
    SELECT date, SUM(amount) as total 
    FROM transactions
    WHERE transactionType = :type AND date BETWEEN :start AND :end
    GROUP BY date
"""
    )
    fun getDailySpending(
        type: Int,
        start: Long,
        end: Long
    ): Flow<List<DailyExpense>>

    @Query(
        """
    SELECT category, SUM(amount) as total
    FROM transactions
    WHERE transactionType = :type AND date BETWEEN :start AND :end
    GROUP BY category
    ORDER BY total DESC
"""
    )
    fun getCategoryBreakdown(
        type: Int,
        start: Long,
        end: Long
    ): Flow<List<CategoryExpense>>

    @Query(
        """
    SELECT COUNT(*) FROM transactions
    WHERE date BETWEEN :start AND :end
"""
    )
    fun getTransactionCount(
        start: Long,
        end: Long
    ): Flow<Int>
}
