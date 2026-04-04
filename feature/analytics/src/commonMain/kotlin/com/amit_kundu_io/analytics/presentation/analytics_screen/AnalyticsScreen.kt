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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import com.amit_kundu_io.analytics.presentation.analytics_screen.MonthlyScreen.MonthlyRootScreen
import com.amit_kundu_io.analytics.presentation.analytics_screen.WeeklyScreen.WeeklyRootScreen
import com.amit_kundu_io.theme.components.GradientHeader.GradientHeader
import com.amit_kundu_io.theme.ui.GradientEnd
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.SmartSpendTheme
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

    var selectedPeriod by remember { mutableStateOf(AnalyticsPeriodTab.WEEK) }
    val periods = listOf(
        AnalyticsPeriodTab.WEEK,
        AnalyticsPeriodTab.MONTH,
        AnalyticsPeriodTab.THREE_MONTHS,
        AnalyticsPeriodTab.YEAR
    )


    Column(
        Modifier.fillMaxSize()
            .background(GradientStart)
            .statusBarsPadding()
            .background(Color.White)
    ) {
        GradientHeader(gradientColors = listOf(GradientStart, GradientEnd)) {
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
                            period.label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }


        when (selectedPeriod) {
            AnalyticsPeriodTab.WEEK -> {
                WeeklyRootScreen()
            }

            AnalyticsPeriodTab.MONTH -> {
                MonthlyRootScreen()
            }

            AnalyticsPeriodTab.THREE_MONTHS -> {}
            AnalyticsPeriodTab.YEAR -> {}
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