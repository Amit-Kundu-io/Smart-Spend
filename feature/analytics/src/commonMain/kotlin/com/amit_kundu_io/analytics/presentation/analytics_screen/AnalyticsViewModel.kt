/**
 * AnalyticsViewModel.kt
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

package com.amit_kundu_io.analytics.presentation.analytics_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.analytics.presentation.analytics_screen.models.CategoryUi
import com.amit_kundu_io.analytics.presentation.analytics_screen.models.DailyUi
import com.amit_kundu_io.database.data.database.dao.CategoryExpense
import com.amit_kundu_io.database.data.database.dao.DailyExpense
import com.amit_kundu_io.database.domain.Repo.AnalyticsRepository
import com.amit_kundu_io.utilities.Data_Models.Category
import com.amit_kundu_io.utilities.Logger.Logger
import com.amit_kundu_io.utilities.global_utility.GlobalUtility
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt
import kotlin.time.Instant

class AnalyticsViewModel(
    private val repo: AnalyticsRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(AnalyticsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                val currentWeekStart = GlobalUtility.getCurrentWeekStartEpoch()
                val currentWeekEnd = GlobalUtility.getCurrentWeekEndEpoch()
                loadData(currentWeekStart, currentWeekEnd)

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AnalyticsState()
        )

    fun onAction(action: AnalyticsAction) {
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

                val dailyUi = mapDailySpendingToUi(daily)
                val categoryUi = mapCategoryToUi(category, total)

                AnalyticsState(
                    isLoading = false,
                    totalSpent = total,
                    avgPerDay = avg,
                    dailySpending = daily,
                    categoryBreakdown = category,
                    transactionCount = count,
                    dailyUi = dailyUi,
                    categoryUi = categoryUi
                )
            }
                .catch { e ->
                    // log error, emit fallback state
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error") }
                }
                .onEach { state ->
                    _state.update { state }
                }
                .launchIn(this)
        }
    }


    fun mapDailySpendingToUi(
        list: List<DailyExpense>
    ): List<DailyUi> {

        val zone = TimeZone.currentSystemDefault()

        return list.map {

            val day = Instant
                .fromEpochMilliseconds(it.date * 1000)
                .toLocalDateTime(zone)
                .dayOfWeek
                .name
                .take(3)

            DailyUi(
                day = day,
                amount = it.total
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

            Logger.d("Calculation_Debug","Total ${total} ,CategoryExpense ${it.total}, Percentage ${percent}")

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