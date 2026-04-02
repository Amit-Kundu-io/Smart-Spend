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
import com.amit_kundu_io.theme.ui.CategoryFood
import com.amit_kundu_io.theme.ui.CategoryHealth
import com.amit_kundu_io.theme.ui.CategoryTransport
import com.amit_kundu_io.theme.ui.CategoryUtil
import com.amit_kundu_io.theme.ui.GradientEnd
import com.amit_kundu_io.theme.ui.GradientPurple
import com.amit_kundu_io.theme.ui.GradientPurpleEnd
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.Success
import com.amit_kundu_io.theme.ui.SuccessContainer

data class Transaction(
    val id: String,
    val title: String,
    val category: String,
    val categoryEmoji: String,
    val categoryColor: Color,
    val amount: Double,
    val isIncome: Boolean,
    val date: String,
    val time: String,
    val paymentMethod: String = "UPI"
)

data class CategorySpending(
    val name: String,
    val emoji: String,
    val amount: Double,
    val percentage: Float,
    val color: Color
)

enum class TransactionType { EXPENSE, INCOME, TRANSFER }

val sampleTransactions = listOf(
    Transaction(
        "1",
        "Grocery Store",
        "Food",
        "🛒", CategoryFood,
        450.0,
        false,
        "Today",
        "2:30 PM"
    ),
    Transaction(
        "2",
        "Ola Cab",
        "Transport",
        "🚗",
        CategoryTransport,
        180.0,
        false,
        "Today",
        "10:00 AM"
    ),
    Transaction(
        "3",
        "Salary Credit",
        "Income",
        "💰",
        SuccessContainer,
        65000.0,
        true,
        "Apr 1",
        "9:00 AM",
        "Bank"
    ),
    Transaction(
        "4",
        "Zomato Order",
        "Food",
        "🍕",
        CategoryFood,
        340.0,
        false,
        "Apr 1",
        "8:15 PM"
    ),
    Transaction(
        "5",
        "Starbucks",
        "Food",
        "☕",
        CategoryFood,
        290.0,
        false,
        "Apr 1",
        "11:30 AM",
        "Card"
    ),
    Transaction(
        "6",
        "Apollo Pharmacy",
        "Health",
        "💊",
        CategoryHealth,
        620.0,
        false,
        "Mar 31",
        "5:00 PM",
        "Card"
    ),
    Transaction(
        "7",
        "Electricity",
        "Utilities",
        "⚡",
        CategoryUtil,
        1200.0,
        false,
        "Mar 31",
        "2:00 PM"
    ),
)

val sampleCategories = listOf(
    CategorySpending("Food & Dining", "🍽", 7200.0, 0.43f, GradientStart),
    CategorySpending("Shopping", "🛍", 5900.0, 0.35f, GradientPurple),
    CategorySpending("Transport", "🚗", 3800.0, 0.23f, Success),
    CategorySpending("Utilities", "⚡", 2400.0, 0.14f, GradientEnd),
    CategorySpending("Entertainment", "🎮", 1850.0, 0.11f, GradientPurpleEnd),
)
