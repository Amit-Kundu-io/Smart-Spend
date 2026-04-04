/**
 * AnalyticsRepository.kt
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

package com.amit_kundu_io.database.domain.Repo

import com.amit_kundu_io.database.data.database.dao.CategoryExpense
import com.amit_kundu_io.database.data.database.dao.DailyExpense
import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {

    fun getTotalExpense(start: Long, end: Long): Flow<Double>

    fun getDailySpending(start: Long, end: Long): Flow<List<DailyExpense>>

    fun getCategoryBreakdown(start: Long, end: Long): Flow<List<CategoryExpense>>

    fun getTransactionCount(start: Long, end: Long): Flow<Int>
}