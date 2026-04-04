/**
 * AnalyticsHeader.kt
 *
 * Author      : Amit Kundu
 * Created On  : 04/04/2026
 */

package com.amit_kundu_io.analytics.presentation.analytics_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnalyticsHeader(
    state: AnalyticsUiState,
    selectedPeriod: AnalyticsPeriod,
    onPeriodChange: (AnalyticsPeriod) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF4527A0), Color(0xFF7B1FA2)),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        Column {
            Text(
                "Analytics",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(14.dp))

            // Period tab row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AnalyticsPeriod.entries.forEach { period ->
                    PeriodTab(
                        label = period.label,
                        isSelected = period == selectedPeriod,
                        onClick = { onPeriodChange(period) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(Modifier.height(18.dp))

            // KPI row
            Row(modifier = Modifier.fillMaxWidth()) {
                KpiItem(
                    "TOTAL SPENT",
                    "₹${formatAmount(state.totalSpent)}",
                    Color.White,
                    Modifier.weight(1f)
                )
                KpiItem(state.kpi2Label, state.kpi2Value, Color.White, Modifier.weight(1f))
                KpiItem(
                    label = state.kpi3Label,
                    value = state.kpi3Value,
                    color = if (state.kpi3IsPositive) Color(0xFFFFCDD2) else Color(0xFFA5F3A0),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PeriodTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg by animateColorAsState(
        if (isSelected) Color.White.copy(alpha = 0.25f) else Color.Transparent,
        label = "tabBg"
    )
    val border by animateColorAsState(
        if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
        label = "tabBorder"
    )
    val fg by animateColorAsState(
        if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
        label = "tabFg"
    )

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = bg, contentColor = fg),
        border = BorderStroke(if (isSelected) 2.dp else 1.5.dp, border),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun KpiItem(label: String, value: String, color: Color, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.75f),
            letterSpacing = 0.4.sp
        )
        Spacer(Modifier.height(3.dp))
        Text(
            value,
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
