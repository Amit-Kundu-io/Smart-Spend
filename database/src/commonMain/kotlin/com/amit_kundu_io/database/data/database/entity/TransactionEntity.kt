package com.amit_kundu_io.database.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["date"]),
        Index(value = ["transactionType"]),
        Index(value = ["category"])
    ]
)
data class TransactionEntity(

    @PrimaryKey
    val id: String,
    val title: String,
    val amount: Double,
    val transactionType: Int,
    val category: Int? = null,
    val paymentMethod: Int,
    val note: String? = null,
    val date: Long
)