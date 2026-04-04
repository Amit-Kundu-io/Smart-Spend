/**
 * MonthlyViewModel.kt
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

package com.amit_kundu_io.analytics.presentation.analytics_screen.MonthlyScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.analytics.presentation.analytics_screen.components.CategoryData
import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import com.amit_kundu_io.database.domain.Repo.AnalyticsRepository
import com.amit_kundu_io.utilities.Data_Models.Category
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.Logger.Logger
import com.amit_kundu_io.utilities.global_utility.GlobalUtility
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

class MonthlyViewModel(
    private val repository: AnalyticsRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MonthlyState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MonthlyState()
        )

    fun onAction(action: MonthlyAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    fun loadMonthlyData() {
        val (startEpoch, endEpoch) = GlobalUtility.currentMonthRange()
        viewModelScope.launch {
            val transactions =
                repository.getCurrentMonthTransactions(startEpoch, endEpoch).firstOrNull()
                    ?: emptyList()

            Logger.d("ANANA", "${transactions}")
            getCurrentMonthHeatmap(transactions)
            calculateWeeklyBars(transactions)
            mapCategories(transactions)
            calculateTotals(transactions)
        }
    }

    fun getCurrentMonthHeatmap(transactions: List<TransactionEntity>) {

        viewModelScope.launch {
            //  Group by day (epoch seconds → LocalDate)
            val tz = TimeZone.currentSystemDefault()
            val groupedByDay = transactions.groupBy {
                Instant.fromEpochSeconds(it.date).toLocalDateTime(tz).date.dayOfMonth
            }

            //  Compute totals per day
            val dailyTotals = getDailyTotals(transactions)

            //  Normalize to 0f–1f
            val maxTotal = dailyTotals.maxOrNull() ?: 1.0
            val heatmapDays = dailyTotals.map { (it / maxTotal).toFloat() }

            // Update state
            _state.update { it.copy(heatmapDays = heatmapDays) }
        }
    }


    fun mapCategories(transactions: List<TransactionEntity>): List<CategoryData> {
        if (transactions.isEmpty()) return emptyList()

        //  Group by category
        val grouped = transactions.groupBy { Category.fromId(it.category ?: 100) }

        //  Total spent overall
        val totalSpent = transactions
            .filter { it.transactionType == TransactionType.EXPENSE.value }
            .sumOf { it.amount }


        //  Map into CategoryData
        val catList = grouped.map { (cat, txns) ->
            val total = txns
                .filter { it.transactionType == TransactionType.EXPENSE.value }
                .sumOf { it.amount }
            val percent = if (totalSpent > 0) (total / totalSpent).toFloat() else 0f
            CategoryData(
                emoji = cat.icon,
                name = cat.label,
                amount = total.toLong(),
                percentage = percent,
                color = Color.Gray
            )
        }.sortedByDescending { it.amount }
        // Update state
        _state.update { it.copy(categories = catList) }
        return catList
    }


    fun calculateWeeklyBars(transactions: List<TransactionEntity>) {
        viewModelScope.launch {
            val tz = TimeZone.currentSystemDefault()
            val now = Clock.System.now().toLocalDateTime(tz)
            val year = now.year
            val month = now.month

            //  First day of month (epoch seconds)
            val startOfMonth = LocalDate(year, month, 1)
                .atStartOfDayIn(tz)
                .toEpochMilliseconds() / 1000

            //  Last day of month
            val lastDayOfMonth = when (month) {
                Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY,
                Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31

                Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
                Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
            }

            //  Number of weeks in this month
            val weekCount = ((lastDayOfMonth - 1) / 7) + 1

            // Pre‑fill totals with 0
            val weekTotals = MutableList(weekCount) { 0L }

            //  Assign transactions to weeks
            transactions.forEach { txn ->
                val diffDays = ((txn.date - startOfMonth) / 86400).toInt()
                val weekIndex = diffDays / 7
                if (weekIndex in weekTotals.indices && txn.transactionType == TransactionType.EXPENSE.value) {
                    weekTotals[weekIndex] += txn.amount.toLong()
                }
            }


            //  Build labeled pairs
            val data = weekTotals.mapIndexed { index, total ->
                "Wk ${index + 1}" to total
            }

            _state.update { it.copy(weeklyBars = data) }
        }
    }


    fun getDailyTotals(transactions: List<TransactionEntity>): List<Double> {
        val tz = TimeZone.currentSystemDefault()
        val now = Clock.System.now().toLocalDateTime(tz)
        val year = now.year
        val month = now.month

        //  Calculate last day of month manually
        val lastDayOfMonth = when (month) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY,
            Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31

            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
        }

        // Group transactions by day of month
        val groupedByDay = transactions.groupBy {
            Instant.fromEpochSeconds(it.date).toLocalDateTime(tz).date.dayOfMonth
        }

        // Build totals for each day in the month
        return (1..lastDayOfMonth).map { day ->
            groupedByDay[day]
                ?.filter { it.transactionType == TransactionType.EXPENSE.value }
                ?.sumOf { it.amount }
                ?: 0.0

        }
    }


    fun calculateTotals(transactions: List<TransactionEntity>): Pair<Double, Double> {
        if (transactions.isEmpty()) return 0.0 to 0.0

        val tz = TimeZone.currentSystemDefault()
        val now = Clock.System.now().toLocalDateTime(tz)
        val year = now.year
        val month = now.month

        //  Calculate last day of month
        val lastDayOfMonth = when (month) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY,
            Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31

            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
        }

        //  Total spent
        // Sum only transactions of type 100
        val totalSpent = transactions
            .filter { it.transactionType == TransactionType.EXPENSE.value }
            .sumOf { it.amount }


        //  Average per day (divide by days in month, not just days with transactions)
        val avgPerDay = if (lastDayOfMonth > 0) totalSpent / lastDayOfMonth else 0.0

        _state.update {
            it.copy(
                totalSpent = totalSpent,
                avgPerDay = avgPerDay

            )
        }

        return totalSpent to avgPerDay
    }


    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

}