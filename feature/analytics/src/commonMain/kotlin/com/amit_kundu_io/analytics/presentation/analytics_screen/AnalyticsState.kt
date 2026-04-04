/**
 * AnalyticsState.kt
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

import androidx.compose.runtime.Immutable

@Immutable
data class AnalyticsState(
    val isLoading: Boolean = false,
    val error: String = "",

    val totalSpent: Double = 0.0,
    val avgPerDay: Double = 0.0,
    val percentageChange: Float = 0f,
    val transactionCount: Int = 0
)