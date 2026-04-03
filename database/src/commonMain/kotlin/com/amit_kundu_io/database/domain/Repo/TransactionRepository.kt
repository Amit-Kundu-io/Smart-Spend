/**
 * TransactionRepository.kt
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

package com.amit_kundu_io.database.domain.Repo

import com.amit_kundu_io.database.data.database.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insert(transaction: TransactionEntity)
    suspend fun update(transaction: TransactionEntity)
    suspend fun delete(transaction: TransactionEntity)

    fun getAll(): Flow<List<TransactionEntity>>
    fun getRecent(): Flow<List<TransactionEntity>>
    fun getByType(type: String): Flow<List<TransactionEntity>>

    fun getTotalExpense(): Flow<Double>
    fun getTotalIncome(): Flow<Double>
    fun getBalance(): Flow<Double>
}