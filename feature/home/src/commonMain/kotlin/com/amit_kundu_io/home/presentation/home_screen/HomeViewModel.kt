/**
 * HomeViewModel.kt
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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.database.domain.Repo.BudgetRepository
import com.amit_kundu_io.database.domain.Repo.TransactionRepository
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.global_utility.GlobalUtility
import com.amit_kundu_io.utilities.global_utility.GlobalUtility.getCurrentYear
import com.amit_kundu_io.utilities.global_utility.GlobalUtility.getMonthEndEpoch
import com.amit_kundu_io.utilities.global_utility.GlobalUtility.getMonthStartEpoch
import com.amit_kundu_io.utilities.global_utility.GlobalUtility.getMonthYearLabel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: TransactionRepository,
    private val budgetRepo: BudgetRepository

) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeState()
        )

    init {
        observeFinancialData()
        observeRecentTransactions()
        observeMonthlyBudget()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            // handle actions
            else -> {}
        }
    }

    //  Combine financial data (optimized)
    private fun observeFinancialData() {
        combine(
            repo.getTotalIncome(),
            repo.getTotalExpense(),
        ) { income, expense ->

            Triple(income, expense, income - expense)

        }
            .onEach { (income, expense, balance) ->
                _state.update {
                    it.copy(
                        totalIncome = income,
                        totalExpense = expense,
                        balance = balance,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }


    //  Recent transactions
    private fun observeRecentTransactions() {
        repo.getRecent()
            .onEach { list ->
                _state.update {
                    it.copy(recentTransactions = list)
                }
            }
            .launchIn(viewModelScope)
    }


    private fun observeMonthlyBudget() {

        val month = GlobalUtility.getCurrentMonth()
        val year = getCurrentYear()
        val start = getMonthStartEpoch()
        val end = getMonthEndEpoch()
        val label = getMonthYearLabel()

        combine(
            repo.getTotalByTypeInRange(
                type = TransactionType.EXPENSE.value,
                start = start,
                end = end
            ).distinctUntilChanged(),

            budgetRepo.getBudget(month, year)
                .distinctUntilChanged()

        ) { expense, budgetEntity ->

            val budget = budgetEntity?.amount ?: 0.0

            val remaining = (budget - expense).coerceAtLeast(0.0)

            val expenseInRupee = expense / 100.0

            val percent = if (budget > 0) {
                ((expenseInRupee / budget) * 100)
                    .coerceIn(0.0, 100.0)
                    .toFloat()
            } else 0f

            Quadruple(expense, budget, remaining, percent)

        }
            .onEach { (expense, budget, remaining, percent) ->
                _state.update {
                    it.copy(
                        monthlySpent = expense,
                        monthlyBudget = budget,
                        idBudgetSet = budget > 0.0,
                        remainingBudget = remaining,
                        budgetUsedPercent = percent,
                        monthLabel = label
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun saveBudget(month: Int, year: Int, amount: String) {
        val value = amount.toDoubleOrNull() ?: return

        viewModelScope.launch {
            budgetRepo.setBudget(
                amount = value,
                month = month,
                year = year
            )
        }
    }

}

data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)