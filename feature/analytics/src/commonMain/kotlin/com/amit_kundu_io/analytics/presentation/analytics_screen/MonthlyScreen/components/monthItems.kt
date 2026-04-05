/**
 * monthItems.kt
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

package com.amit_kundu_io.analytics.presentation.analytics_screen.MonthlyScreen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.analytics.presentation.analytics_screen.MonthlyScreen.MonthlyState
import com.amit_kundu_io.analytics.presentation.analytics_screen.components.AnalyticsCard
import com.amit_kundu_io.analytics.presentation.analytics_screen.components.CategoryBreakdownCard
import com.amit_kundu_io.analytics.presentation.analytics_screen.components.SpendingHeatmap
import com.amit_kundu_io.analytics.presentation.analytics_screen.components.StatsGrid
import com.amit_kundu_io.analytics.presentation.analytics_screen.components.formatAmount
import com.amit_kundu_io.theme.ui.SmartSpendTheme

fun LazyListScope.monthItems(state: MonthlyState) {
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
    //item { StatsGrid(stats = state.stats) }
}

@Preview
@Composable
private fun showUi(){
    SmartSpendTheme {
        LazyColumn {
            monthItems(state = MonthlyState())
        }
    }
}
