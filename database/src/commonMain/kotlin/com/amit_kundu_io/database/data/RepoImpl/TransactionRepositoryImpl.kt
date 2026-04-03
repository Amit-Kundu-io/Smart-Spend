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

import com.amit_kundu_io.database.data.database.dao.TransactionDao
import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import com.amit_kundu_io.database.domain.Repo.TransactionRepository
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val dao: TransactionDao
) : TransactionRepository {

    // -------------------------------
    // CRUD
    // -------------------------------

    override suspend fun insert(transaction: TransactionEntity) {
        dao.insert(transaction)
    }

    override suspend fun update(transaction: TransactionEntity) {
        dao.update(transaction)
    }

    override suspend fun delete(transaction: TransactionEntity) {
        dao.delete(transaction)
    }

    override fun observeById(id: String): Flow<TransactionEntity?> {
        return dao.observeById(id)
    }

    // -------------------------------
    // FETCH
    // -------------------------------

    override fun getAll(): Flow<List<TransactionEntity>> =
        dao.getAll()

    override fun getRecent(): Flow<List<TransactionEntity>> =
        dao.getRecent()

    override fun getByType(type: TransactionType): Flow<List<TransactionEntity>> =
        dao.getByType(type.value)

    // -------------------------------
    // FINANCIAL SUMMARY
    // -------------------------------

    override fun getTotalIncome(): Flow<Double> =
        dao.getTotalByType(TransactionType.INCOME.value)

    override fun getTotalByTypeInRange(
        type: Int,
        start: Long,
        end: Long
    ): Flow<Double> {
        return dao.getTotalByTypeInRange(
            type = type,
            start = start,
            end = end
        )
    }

    override suspend fun getPage(
        page: Int,
        pageSize: Int,
        type: Int?
    ): List<TransactionEntity> {
        val offset = page * pageSize
        return dao.getTransactionsPagedByType(
            type = type,
            limit = pageSize,
            offset = offset
        )
    }


    override fun getTotalExpense(): Flow<Double> =
        dao.getTotalByType(TransactionType.EXPENSE.value)


//    override fun getCategoryWiseExpense(): Flow<List<CategorySum>> =
//        dao.getCategoryWiseTotal(TransactionType.EXPENSE.value)
//
//    override fun getPaymentStats(): Flow<List<PaymentMethodSum>> =
//        dao.getPaymentMethodStats()
}