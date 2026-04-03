package com.amit_kundu_io.database.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["date"]),
        Index(value = ["type"]),
        Index(value = ["category"])
    ]
)
data class TransactionEntity(

    @PrimaryKey
    val id: String,
    val title: String,
    val amount: Double,
    val type: Int,
    val category: Int? = null,
    val paymentMethod: Int,
    val note: String? = null,
    val date: Long
)
