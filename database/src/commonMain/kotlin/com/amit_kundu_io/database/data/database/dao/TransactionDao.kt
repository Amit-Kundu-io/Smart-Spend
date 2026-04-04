package com.amit_kundu_io.database.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {



        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(entity: TransactionEntity)

        @Update
        suspend fun update(entity: TransactionEntity)

        @Delete
        suspend fun delete(entity: TransactionEntity)

        @Query("DELETE FROM transactions WHERE id = :id")
        suspend fun deleteById(id: String)

        @Query("""
        SELECT * FROM transactions 
        ORDER BY date DESC
    """)
        fun getAll(): Flow<List<TransactionEntity>>

        @Query("""
        SELECT * FROM transactions 
        ORDER BY date DESC 
        LIMIT :limit
    """)
        fun getRecent(limit: Int = 10): Flow<List<TransactionEntity>>

        @Query(
                """
        SELECT * FROM transactions 
        WHERE transactionType = :type 
        ORDER BY date DESC
    """
        )
        fun getByType(type: Int): Flow<List<TransactionEntity>>


        @Query(
                """
        SELECT IFNULL(SUM(amount), 0) 
        FROM transactions 
        WHERE transactionType = :type
    """
        )
        fun getTotalByType(type: Int): Flow<Double>

        @Query(
                """
        SELECT IFNULL(SUM(amount), 0) 
        FROM transactions 
        WHERE transactionType = :type 
        AND date BETWEEN :start AND :end
    """
        )
        fun getTotalByTypeInRange(
                type: Int,
                start: Long,
                end: Long
        ): Flow<Double>


        @Query("DELETE FROM transactions")
        suspend fun clearAll()

        @Query(
                """
    SELECT * FROM transactions
    WHERE (:type IS NULL OR transactionType = :type)
    ORDER BY date DESC
    LIMIT :limit OFFSET :offset
"""
        )
        suspend fun getTransactionsPagedByType(
                type: Int?,
                limit: Int,
                offset: Int
        ): List<TransactionEntity>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAll(list: List<TransactionEntity>)

        @Query("SELECT COUNT(*) FROM transactions")
        suspend fun getCount(): Int

        @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
        fun observeById(id: String): Flow<TransactionEntity?>

        @Query(
                """
    SELECT * FROM transactions
    WHERE 
        (:type IS NULL OR transactionType = :type)

        AND (:startDate IS NULL OR date >= :startDate)
        AND (:endDate IS NULL OR date <= :endDate)

        AND (
            :query IS NULL OR :query = '' OR
            LOWER(title) LIKE '%' || LOWER(:query) || '%' OR
            CAST(amount AS TEXT) LIKE '%' || :query || '%'
        )

    ORDER BY date DESC
    LIMIT :limit OFFSET :offset
"""
        )
        suspend fun getTransactionsPaged(
                type: Int?,
                query: String?,
                startDate: Long?,
                endDate: Long?,
                limit: Int,
                offset: Int
        ): List<TransactionEntity>
}