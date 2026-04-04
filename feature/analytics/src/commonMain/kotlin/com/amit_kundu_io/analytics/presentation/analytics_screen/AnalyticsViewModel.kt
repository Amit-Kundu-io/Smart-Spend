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
import com.amit_kundu_io.database.domain.Repo.AnalyticsRepository
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
                repo.getTransactionCount(start, end)
            ) { total, count ->

                val days = ((end - start) / (24 * 60 * 60)).coerceAtLeast(1)
                val avg = total / days

                AnalyticsState(
                    isLoading = false,
                    totalSpent = total,
                    avgPerDay = avg,
                    transactionCount = count,
                )
            }
                .catch { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error") }
                }
                .onEach { state ->
                    _state.update { state }
                }
                .launchIn(this)
        }
    }

}