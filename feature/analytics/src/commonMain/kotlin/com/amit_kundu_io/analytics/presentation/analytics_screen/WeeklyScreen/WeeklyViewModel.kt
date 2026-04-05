/**
 * WeeklyViewModel.kt
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

package com.amit_kundu_io.analytics.presentation.analytics_screen.WeeklyScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.analytics.presentation.analytics_screen.models.CategoryUi
import com.amit_kundu_io.database.data.database.dao.CategoryExpense
import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import com.amit_kundu_io.database.domain.Repo.AnalyticsRepository
import com.amit_kundu_io.theme.components.Bar.SpendWiseBarChart.BarData
import com.amit_kundu_io.utilities.Data_Models.Category
import com.amit_kundu_io.utilities.Logger.Logger
import com.amit_kundu_io.utilities.global_utility.GlobalUtility
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt
import kotlin.time.Instant

class WeeklyViewModel(
    private val repo: AnalyticsRepository

) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(WeeklyState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                val currentWeekStart = GlobalUtility.getCurrentWeekStartEpoch()
                val currentWeekEnd = GlobalUtility.getCurrentWeekEndEpoch()
                loadData(currentWeekStart, currentWeekEnd)
                getCurrentWeekData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WeeklyState()
        )


    fun onAction(action: WeeklyAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    private fun loadData(start: Long, end: Long) {
        viewModelScope.launch {
            combine(
                repo.getTotalExpense(start, end),
                repo.getDailySpending(start, end),
                repo.getCategoryBreakdown(start, end),
                repo.getTransactionCount(start, end)
            ) { total, daily, category, count ->

                val days = ((end - start) / (24 * 60 * 60)).coerceAtLeast(1) // dynamic day count
                val avg = total / days

                val categoryUi = mapCategoryToUi(category, total)

                WeeklyState(
                    isLoading = false,
                    totalSpent = total,
                    avgPerDay = avg,
                    dailySpending = daily,
                    categoryBreakdown = category,
                    transactionCount = count,
                    categoryUi = categoryUi
                )
            }
                .catch { e ->
                    // log error, emit fallback state
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error") }
                }
                .onEach { state ->
                    _state.update {
                        it.copy(
                            isLoading = state.isLoading,
                            totalSpent = state.totalSpent,
                            avgPerDay = state.avgPerDay,
                            dailySpending = state.dailySpending,
                            categoryBreakdown = state.categoryBreakdown,
                            transactionCount = state.transactionCount,
                            categoryUi = state.categoryUi
                        )
                    }
                }
                .launchIn(this)
        }

    }

    fun getCurrentWeekData() {
        viewModelScope.launch {
            val (startEpoch, endEpoch) = GlobalUtility.currentWeekRange()
            val data =
                repo.getCurrentMonthTransactions(startEpoch, endEpoch).firstOrNull() ?: emptyList()
            val uidata = mapWeekTransactionsToDailyUi(data)
            Logger.d("TAGGAG", "VM getCurrentWeekData: $uidata")
            _state.update {
                it.copy(
                    weeklyData = uidata
                )
            }

            Logger.d("TAGGAG", "VM getCurrentWeekData: ${state.value.weeklyData}")

        }

    }


    fun mapWeekTransactionsToDailyUi(transactions: List<TransactionEntity>): List<BarData> {

        if (transactions.isNullOrEmpty()) return emptyList()

        val tz = TimeZone.currentSystemDefault()

        // Group transactions by day of week
        val grouped = transactions.groupBy {
            Instant.fromEpochSeconds(it.date)
                .toLocalDateTime(tz)
                .date.dayOfWeek
        }

        // Ensure all 7 days are present (Sunday → Saturday)
        val daysOfWeek = listOf(
            DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        )

        // Collect raw totals
        val rawTotals = daysOfWeek.map { dow ->
            grouped[dow]?.sumOf { it.amount } ?: 0.0
        }

        // Find max for normalization
        val maxTotal = rawTotals.maxOrNull() ?: 1.0

        // Map into BarData with normalized values
        return daysOfWeek.mapIndexed { index, dow ->
            val normalized = (rawTotals[index] / maxTotal).toFloat()
            Logger.d("TAGGAG", "VM mapWeekTransactionsToDailyUi: $normalized")
            BarData(
                label = dow.name.lowercase().replaceFirstChar { it.uppercase() }
                    .take(3), // "Sun", "Mon", etc.
                value = normalized
            )
        }
    }


    fun mapCategoryToUi(
        list: List<CategoryExpense>,
        total: Double
    ): List<CategoryUi> {

        return list.map {

            val percent = if (total > 0) {
                ((it.total / total) * 100).toFloat()
            } else 0f

            val rounded = (percent * 10).roundToInt() / 10f

            Logger.d(
                "Calculation_Debug",
                "Total ${total} ,CategoryExpense ${it.total}, Percentage ${percent}"
            )

            val cat = Category.fromId(it.category ?: 100)

            CategoryUi(
                name = cat.label,
                amount = it.total,
                percent = rounded,
                emoji = cat.icon
            )
        }
    }

}