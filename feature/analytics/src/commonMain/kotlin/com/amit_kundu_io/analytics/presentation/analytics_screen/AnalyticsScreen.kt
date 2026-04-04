/**
 * AnalyticsScreen.kt
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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.theme.CategorySpending
import com.amit_kundu_io.theme.components.Bar.CategorySpendingBar.CategorySpendingBar
import com.amit_kundu_io.theme.components.Bar.SpendWiseBarChart.BarData
import com.amit_kundu_io.theme.components.Bar.SpendWiseBarChart.SpendWiseBarChart
import com.amit_kundu_io.theme.components.GradientHeader.GradientHeader
import com.amit_kundu_io.theme.components.cards.StatCardsGrid
import com.amit_kundu_io.theme.sampleCategories
import com.amit_kundu_io.theme.ui.GradientPurple
import com.amit_kundu_io.theme.ui.GradientPurpleEnd
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.SmartSpendTheme
import com.amit_kundu_io.utilities.global_utility.GlobalUtility
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AnalyticsRootScreen(
    viewModel: AnalyticsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AnalyticsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun AnalyticsScreen(
    state: AnalyticsState,
    onAction: (AnalyticsAction) -> Unit,
) {

    var selectedPeriod by remember { mutableStateOf("Week") }
    val periods = listOf("Week", "Month", "3M", "Year")
    val barData = state.dailyUi.map {
        BarData(
            label = it.day,
            value = it.amount.toFloat(),
            isHighlighted = false
        )
    }

    SpendWiseBarChart(
        bars = barData,
        modifier = Modifier.fillMaxWidth()
    )

    Column(modifier = Modifier.fillMaxSize()) {
        GradientHeader(gradientColors = listOf(GradientPurple, GradientPurpleEnd)) {
            Text(
                "Analytics",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(14.dp))
            // Period selector
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                periods.forEach { period ->
                    val isSelected = selectedPeriod == period
                    OutlinedButton(
                        onClick = { selectedPeriod = period },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) Color.White.copy(
                                alpha = 0.2f
                            ) else Color.Transparent,
                            contentColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
                        ),
                        border = BorderStroke(
                            if (isSelected) 1.5.dp else 1.dp,
                            if (isSelected) Color.White else Color.White.copy(alpha = 0.4f)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text(
                            period,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Column {
                    Text(
                        "TOTAL SPENT",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.75f)
                    ); Spacer(Modifier.height(2.dp)); Text(
                    "₹${GlobalUtility.formatCurrency(state.totalSpent)}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                }
                Column {
                    Text(
                        "AVG/DAY",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.75f)
                    ); Spacer(Modifier.height(2.dp)); Text(
                    "₹${GlobalUtility.formatCurrency(state.avgPerDay)}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                }
                Column {
                    Text(
                        "VS LAST",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.75f)
                    ); Spacer(Modifier.height(2.dp)); Text(
                    "${state.percentageChange.toInt()}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFA5F3A0),
                    fontWeight = FontWeight.Bold
                )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                ElevatedCard(shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Daily Spending (₹)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(12.dp))
                        SpendWiseBarChart(bars = barData, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
            item {
                ElevatedCard(shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Category Breakdown",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(12.dp))
                        state.categoryUi.forEach { CategorySpendingBar(CategorySpending(
                            name = it.name,
                            emoji = it.emoji,
                            amount = it.amount,
                            percentage = it.percent,
                            color = GradientStart
                        )) }
                    }
                }
            }
            item { StatCardsGrid() }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SmartSpendTheme {
        AnalyticsScreen(
            state = AnalyticsState(),
            onAction = {}
        )
    }
}