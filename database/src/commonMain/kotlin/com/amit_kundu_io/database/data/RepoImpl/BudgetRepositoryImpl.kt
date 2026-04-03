/**
 * BudgetRepositoryImpl.kt
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

package com.amit_kundu_io.database.data.RepoImpl

import com.amit_kundu_io.database.data.database.dao.BudgetDao
import com.amit_kundu_io.database.data.database.entity.BudgetEntity
import com.amit_kundu_io.database.domain.Repo.BudgetRepository
import kotlinx.coroutines.flow.Flow

class BudgetRepositoryImpl(
    private val dao: BudgetDao
) : BudgetRepository {
    override fun getBudget(
        month: Int,
        year: Int
    ): Flow<BudgetEntity?> = dao.getBudget(month, year)

    override suspend fun setBudget(amount: Double, month: Int, year: Int) {
        dao.insert(
            BudgetEntity(
                amount = amount,
                month = month,
                year = year
            )
        )
    }
}