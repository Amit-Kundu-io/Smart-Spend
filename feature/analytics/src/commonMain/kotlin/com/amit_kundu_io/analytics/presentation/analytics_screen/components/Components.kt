/**
 * Components.kt
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

package com.amit_kundu_io.analytics.presentation.analytics_screen.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun AnalyticsScreen() {
    var selectedPeriod by remember { mutableStateOf(AnalyticsPeriod.WEEK) }
    val state by remember(selectedPeriod) { derivedStateOf { analyticsStateFor(selectedPeriod) } }

    Column(modifier = Modifier.fillMaxSize()) {

        // ── Sticky gradient header ────────────────────────────
        AnalyticsHeader(
            state = state,
            selectedPeriod = selectedPeriod,
            onPeriodChange = { selectedPeriod = it }
        )

        // ── Scrollable content — switches by period ───────────
        AnimatedContent(
            targetState = selectedPeriod,
            transitionSpec = {
                val direction = if (targetState.ordinal > initialState.ordinal)
                    AnimatedContentTransitionScope.SlideDirection.Start
                else AnimatedContentTransitionScope.SlideDirection.End
                slideIntoContainer(direction, tween(300)) togetherWith
                        slideOutOfContainer(direction, tween(300))
            },
            label = "periodContent"
        ) { period ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (period) {
                    AnalyticsPeriod.WEEK -> weekItems(state)
                    AnalyticsPeriod.MONTH -> monthItems(state)
                    AnalyticsPeriod.THREE_MONTHS -> threeMonthItems(state)
                    AnalyticsPeriod.YEAR -> yearItems(state)
                }
            }
        }
    }
}
