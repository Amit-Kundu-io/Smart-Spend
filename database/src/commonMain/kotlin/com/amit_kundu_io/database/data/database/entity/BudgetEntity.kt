/**
 * BudgetEntity.kt
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

package com.amit_kundu_io.database.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budgets",
    indices = [Index(value = ["month", "year"], unique = true)]
)
data class BudgetEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val amount: Double,

    val month: Int, // 1–12
    val year: Int   // 2026
)