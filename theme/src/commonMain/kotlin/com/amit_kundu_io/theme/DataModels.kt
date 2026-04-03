/**
 * DataModels.kt
 *
 * Author      : Amit Kundu
 * Created On  : 02/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.theme

import androidx.compose.ui.graphics.Color
import com.amit_kundu_io.theme.ui.GradientEnd
import com.amit_kundu_io.theme.ui.GradientPurple
import com.amit_kundu_io.theme.ui.GradientPurpleEnd
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.Success
import com.amit_kundu_io.utilities.Data_Models.PaymentMethod
import com.amit_kundu_io.utilities.Data_Models.TransactionType

data class Transaction(
    val id: String,
    val title: String,
    val category: Int,
    val categoryEmoji: String,
    val categoryColor: Color,
    val amount: Double,
    val type: TransactionType?,
    val date: String,
    val time: String,
    val paymentMethod: PaymentMethod?
)

data class CategorySpending(
    val name: String,
    val emoji: String,
    val amount: Double,
    val percentage: Float,
    val color: Color
)


val sampleCategories = listOf(
    CategorySpending("Food & Dining", "🍽", 7200.0, 0.43f, GradientStart),
    CategorySpending("Shopping", "🛍", 5900.0, 0.35f, GradientPurple),
    CategorySpending("Transport", "🚗", 3800.0, 0.23f, Success),
    CategorySpending("Utilities", "⚡", 2400.0, 0.14f, GradientEnd),
    CategorySpending("Entertainment", "🎮", 1850.0, 0.11f, GradientPurpleEnd),
)
