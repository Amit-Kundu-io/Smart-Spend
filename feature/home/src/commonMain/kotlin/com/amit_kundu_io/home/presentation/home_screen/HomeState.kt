/**
 * HomeState.kt
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

package com.amit_kundu_io.home.presentation.home_screen

import androidx.compose.runtime.Immutable
import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import com.amit_kundu_io.utilities.global_utility.GlobalUtility.getCurrentMonth
import com.amit_kundu_io.utilities.global_utility.GlobalUtility.getCurrentYear

@Immutable
data class HomeState(
    val isLoading: Boolean = false,
    val recentTransactions: List<TransactionEntity> = emptyList(),

    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val balance: Double = 0.0,

    // Budget
    val monthlySpent: Double = 0.0,
    val monthlyBudget: Double = 0.0,
    val remainingBudget: Double = 0.0,
    val budgetUsedPercent: Float = 0f,
    val monthLabel: String = "",

    val idBudgetSet : Boolean = false,

    val selectedMonth: Int = getCurrentMonth(),
    val selectedYear: Int = getCurrentYear(),
    val amount: String = ""
)