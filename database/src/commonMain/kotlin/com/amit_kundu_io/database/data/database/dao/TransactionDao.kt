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



        @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
        suspend fun insert(entity: TransactionEntity)

        @Update
        suspend fun update(entity: TransactionEntity)

        @Delete
        suspend fun delete(entity: TransactionEntity)

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

        @Query("""
        SELECT * FROM transactions 
        WHERE type = :type 
        ORDER BY date DESC
    """)
        fun getByType(type: Int): Flow<List<TransactionEntity>>


        @Query("""
        SELECT IFNULL(SUM(amount), 0) 
        FROM transactions 
        WHERE type = :type
    """)
        fun getTotalByType(type: Int): Flow<Double>

        @Query("""
        SELECT IFNULL(SUM(amount), 0) 
        FROM transactions 
        WHERE type = :type 
        AND date BETWEEN :start AND :end
    """)
        fun getTotalByTypeInRange(
            type: Int,
            start: Long,
            end: Long
        ): Flow<Double>


        @Query("DELETE FROM transactions")
        suspend fun clearAll()


    }