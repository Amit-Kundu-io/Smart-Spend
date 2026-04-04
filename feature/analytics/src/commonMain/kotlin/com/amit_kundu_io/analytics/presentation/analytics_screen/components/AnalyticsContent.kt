/**
 * AnalyticsContent.kt
 *
 * Author      : Amit Kundu
 * Created On  : 04/04/2026
 */

package com.amit_kundu_io.analytics.presentation.analytics_screen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

fun LazyListScope.weekItems(state: AnalyticsUiState) {
    item {
        AnalyticsCard(title = "Daily Spending (₹)") {
            SpendWiseBarChart(bars = state.bars)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Peak: Wed ₹1,200",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TrendBadge(value = "-12%", isPositive = false)
            }
        }
    }
    item { CategoryBreakdownCard(categories = state.categories) }
    item { StatsGrid(stats = state.stats) }
}

fun LazyListScope.monthItems(state: AnalyticsUiState) {
    item {
        AnalyticsCard(title = "April 2026 — Daily Spend") {
            SpendingHeatmap(days = state.heatmapDays)
        }
    }
    item {
        AnalyticsCard(title = "Weekly Breakdown") {
            val maxWeek = state.weeklyBars.maxOfOrNull { it.second }?.toFloat() ?: 1f
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                state.weeklyBars.forEach { (label, amount) ->
                    val progress = (amount.toFloat() / maxWeek).coerceIn(0f, 1f)
                    val animP by animateFloatAsState(
                        targetValue = progress,
                        animationSpec = tween(600),
                        label = "weekBar"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            label,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.width(36.dp)
                        )
                        Box(
                            modifier = Modifier.weight(1f).height(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxHeight().fillMaxWidth(animP)
                                    .clip(RoundedCornerShape(5.dp)).background(Color(0xFF4527A0))
                            )
                        }
                        Text(
                            "₹${formatAmount(amount)}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.width(56.dp),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
    item { CategoryBreakdownCard(categories = state.categories) }
    item { StatsGrid(stats = state.stats) }
}

fun LazyListScope.threeMonthItems(state: AnalyticsUiState) {
    item {
        AnalyticsCard(title = "Monthly Comparison") {
            SpendWiseBarChart(bars = state.bars)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LegendItem(color = Color(0xFF4527A0), label = "Spent")
            }
        }
    }
    item {
        AnalyticsCard(title = "Month Summary") {
            Column {
                state.monthSummaries.forEachIndexed { idx, month ->
                    MonthSummaryRow(month = month)
                    if (idx < state.monthSummaries.lastIndex) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }
            }
        }
    }
    item { CategoryBreakdownCard(categories = state.categories) }
    item { StatsGrid(stats = state.stats) }
}

fun LazyListScope.yearItems(state: AnalyticsUiState) {
    item {
        AnalyticsCard(title = "Monthly Spending — 2026") {
            SpendWiseBarChart(bars = state.bars)
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Jan–Apr recorded",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TrendBadge(value = "May–Dec projected", isPositive = null)
            }
        }
    }
    item {
        AnalyticsCard(title = "Quarter Breakdown") {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                state.quarters.forEachIndexed { idx, (label, subtitle, amount) ->
                    val isActive = amount != null
                    val isCurrent = idx == 1
                    QuarterCard(
                        label = label,
                        subtitle = subtitle,
                        amount = amount,
                        isCurrent = isCurrent,
                        isFaded = !isActive,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
    item { CategoryBreakdownCard(categories = state.categories) }
    item { StatsGrid(stats = state.stats) }
}
