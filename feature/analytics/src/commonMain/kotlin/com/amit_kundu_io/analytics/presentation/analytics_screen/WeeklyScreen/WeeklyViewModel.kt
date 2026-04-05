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
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt
import kotlin.time.Clock
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

            Logger.d("TAdsdsdsdGGAG", "startDate: ${startEpoch} endDate ${endEpoch}")

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


    fun mapWeekTransactionsToDailyUi(
        transactions: List<TransactionEntity>
    ): List<BarData> {
        Logger.d("TAdsdsdsdGGAG", "Data: ${transactions}")

        if (transactions.isEmpty()) return emptyList()

        val tz = TimeZone.currentSystemDefault()
        val today = Clock.System.now().toLocalDateTime(tz).date

        // Sunday start fix
        val startOfWeek = today.minus((today.dayOfWeek.ordinal + 1) % 7, DateTimeUnit.DAY)
        val endOfWeek = startOfWeek.plus(6, DateTimeUnit.DAY)

        val weekTransactions = transactions.filter {
            val txnDate = Instant.fromEpochSeconds(it.date)
                .toLocalDateTime(tz).date
            txnDate in startOfWeek..endOfWeek
        }

        val grouped = weekTransactions.groupBy {
            Instant.fromEpochSeconds(it.date)
                .toLocalDateTime(tz).date.dayOfWeek
        }

        val daysOfWeek = listOf(
            DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
        )

        val rawTotals = daysOfWeek.map { dow ->
            grouped[dow]?.sumOf { it.amount } ?: 0.0
        }

        val maxTotal = rawTotals.maxOrNull()?.takeIf { it > 0 } ?: 1.0

        return daysOfWeek.mapIndexed { index, dow ->

            val normalized = if (rawTotals[index] == 0.0) {
                0f
            } else {
                (rawTotals[index] / maxTotal).toFloat().coerceIn(0.1f, 1f)
            }

            val isToday = dow == today.dayOfWeek

            BarData(
                label = dow.name.take(3).lowercase()
                    .replaceFirstChar { it.uppercase() },
                value = normalized,
                isHighlighted = isToday
            )
        }
    }

    fun generateWeeklyDummyData(): List<TransactionEntity> {
        val tz = TimeZone.currentSystemDefault()
        val now = Clock.System.now()

        return listOf(
            TransactionEntity("1", "Food", 200.0, 100, 1, 1, null, now.minus(0, DateTimeUnit.DAY, tz).epochSeconds),
            TransactionEntity("2", "Recharge", 150.0, 100, 1, 1, null, now.minus(1, DateTimeUnit.DAY, tz).epochSeconds),
            TransactionEntity("3", "Bills", 500.0, 101, 1, 1, null, now.minus(2, DateTimeUnit.DAY, tz).epochSeconds),
            TransactionEntity("4", "Shopping", 800.0, 100, 1, 1, null, now.minus(3, DateTimeUnit.DAY, tz).epochSeconds),
            TransactionEntity("5", "Travel", 300.0, 100, 1, 1, null, now.minus(4, DateTimeUnit.DAY, tz).epochSeconds),
            TransactionEntity("6", "Snacks", 100.0, 100, 1, 1, null, now.minus(5, DateTimeUnit.DAY, tz).epochSeconds),
            TransactionEntity("7", "Groceries", 600.0, 100, 1, 1, null, now.minus(6, DateTimeUnit.DAY, tz).epochSeconds),
        )
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