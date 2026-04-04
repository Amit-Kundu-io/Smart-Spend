/**
 * MonthlyState.kt
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

import androidx.compose.runtime.Immutable
import com.amit_kundu_io.analytics.presentation.analytics_screen.components.CategoryData
import com.amit_kundu_io.analytics.presentation.analytics_screen.components.MonthSummary

@Immutable
data class MonthlyState(
    val isLoading: Boolean = false,
    val totalSpent: Double = 0.0,
    val avgPerDay: Double = 0.0,
    val percentageChange: Float = 0f,

    val categories: List<CategoryData> = emptyList(),
    val stats: List<Pair<String, String>> = emptyList(),
    val weeklyBars: List<Pair<String, Long>> = emptyList(),
    val heatmapDays: List<Float> =emptyList(),
    val monthSummaries: List<MonthSummary> = emptyList(),
)