/**
 * WeeklyScreen.kt
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.theme.CategorySpending
import com.amit_kundu_io.theme.components.Bar.CategorySpendingBar.CategorySpendingBar
import com.amit_kundu_io.theme.components.Bar.SpendWiseBarChart.SpendWiseBarChart
import com.amit_kundu_io.theme.components.GradientHeader.GradientHeader
import com.amit_kundu_io.theme.components.cards.StatCardsGrid
import com.amit_kundu_io.theme.ui.GradientPurple
import com.amit_kundu_io.theme.ui.GradientPurpleEnd
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.SmartSpendTheme
import com.amit_kundu_io.utilities.Logger.Logger
import com.amit_kundu_io.utilities.global_utility.GlobalUtility
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WeeklyRootScreen(
    viewModel: WeeklyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.weeklyData) {
        Logger.d("TAGGAG", " ui ${state.weeklyData}")
    }

    WeeklyScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun WeeklyScreen(
    state: WeeklyState,
    onAction: (WeeklyAction) -> Unit,
) {

    Column(modifier = Modifier.fillMaxSize()) {
        GradientHeader(gradientColors = listOf(GradientPurple, GradientPurpleEnd)) {
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
                        SpendWiseBarChart(bars = state.weeklyData, modifier = Modifier.fillMaxWidth())
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
                        state.categoryUi.forEach {
                            CategorySpendingBar(
                                CategorySpending(
                                    name = it.name,
                                    emoji = it.emoji,
                                    amount = it.amount,
                                    percentage = it.percent,
                                    color = GradientStart
                                )
                            )
                        }
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
        WeeklyScreen(
            state = WeeklyState(),
            onAction = {}
        )
    }
}