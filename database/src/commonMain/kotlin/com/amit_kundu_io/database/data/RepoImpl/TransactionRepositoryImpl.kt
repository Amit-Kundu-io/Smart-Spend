/**
 * TransactionRepositoryImpl.kt
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

import com.amit_kundu_io.database.data.database.TransactionDao
import com.amit_kundu_io.database.data.database.TransactionEntity
import com.amit_kundu_io.database.domain.Repo.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val dao: TransactionDao
) : TransactionRepository {

    override suspend fun insert(transaction: TransactionEntity) {
        dao.insert(transaction)
    }

    override suspend fun update(transaction: TransactionEntity) {
        dao.update(transaction)
    }

    override suspend fun delete(transaction: TransactionEntity) {
        dao.delete(transaction)
    }

    override fun getAll(): Flow<List<TransactionEntity>> =
        dao.getAll().map { it.map { it } }

    override fun getRecent(): Flow<List<TransactionEntity>> =
        dao.getRecent().map { it.map { it } }

    override fun getByType(type: String): Flow<List<TransactionEntity>> =
        dao.getByType(type).map { it.map { it } }

    override fun getTotalExpense(): Flow<Double> = dao.getTotalExpense()

    override fun getTotalIncome(): Flow<Double> = dao.getTotalIncome()

    override fun getBalance(): Flow<Double> = dao.getBalance()
}