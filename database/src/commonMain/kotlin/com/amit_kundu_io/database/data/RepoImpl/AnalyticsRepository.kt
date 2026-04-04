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

package com.amit_kundu_io.database.data.RepoImpl

import com.amit_kundu_io.database.data.database.dao.AnalyticsDao
import com.amit_kundu_io.database.data.database.dao.CategoryExpense
import com.amit_kundu_io.database.data.database.dao.DailyExpense
import com.amit_kundu_io.database.data.database.dao.TransactionDao
import com.amit_kundu_io.database.domain.Repo.AnalyticsRepository
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import kotlinx.coroutines.flow.Flow

class AnalyticsRepositoryImpl(
    private val dao: AnalyticsDao
) : AnalyticsRepository {

    override fun getTotalExpense(start: Long, end: Long): Flow<Double> {
        return dao.getTotalByTypeInRange(
            type = TransactionType.EXPENSE.value,
            start = start,
            end = end
        )
    }

    override fun getDailySpending(start: Long, end: Long): Flow<List<DailyExpense>> {
        return dao.getDailySpending(
            type = TransactionType.EXPENSE.value,
            start = start,
            end = end
        )
    }

    override fun getCategoryBreakdown(start: Long, end: Long): Flow<List<CategoryExpense>> {
        return dao.getCategoryBreakdown(
            type = TransactionType.EXPENSE.value,
            start = start,
            end = end
        )
    }

    override fun getTransactionCount(start: Long, end: Long): Flow<Int> {
        return dao.getTransactionCount(start, end)
    }
}