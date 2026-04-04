/**
 * DailyExpense.kt
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

package com.amit_kundu_io.database.data.database.dao

data class DailyExpense(
    val date: Long,
    val total: Double
)

data class CategoryExpense(
    val category: Int?,
    val total: Double
)