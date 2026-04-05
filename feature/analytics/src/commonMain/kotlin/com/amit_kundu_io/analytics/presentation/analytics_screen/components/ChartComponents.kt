/**
 * ChartComponents.kt
 *
 * Author      : Amit Kundu
 * Created On  : 04/04/2026
 */

package com.amit_kundu_io.analytics.presentation.analytics_screen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpendWiseBarChart(bars: List<BarData>, modifier: Modifier = Modifier) {
    if (bars.isEmpty()) return

    val maxVal = bars.maxOfOrNull { it.value }?.takeIf { it > 0f } ?: 1f

    Row(
        modifier = modifier.height(100.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        bars.forEach { bar ->
            val ratio = if (bar.isFaded) 0.2f else (bar.value / maxVal).coerceIn(0f, 1f)
            val animatedH by animateFloatAsState(
                targetValue = ratio,
                animationSpec = tween(600),
                label = "bar_${bar.label}"
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.weight((1f - animatedH).coerceAtLeast(0.01f)))
                Box(
                    modifier = Modifier
                        .weight(animatedH.coerceAtLeast(0.05f))
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
                        .background(
                            when {
                                bar.isHighlighted -> Color(0xFF4527A0)
                                bar.isFaded -> Color(0xFFD1C4E9).copy(alpha = 0.5f)
                                else -> Color(0xFFD1C4E9)
                            }
                        )
                        .alpha(if (bar.isFaded) 0.5f else 1f)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    bar.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    fontSize = 9.sp
                )
            }
        }
    }
}

@Composable
fun SpendingHeatmap(days: List<Float>, modifier: Modifier = Modifier) {
    val dayLabels = listOf("S", "M", "T", "W", "T", "F", "S")
    val lowColor = Color(0xFFEBE7EF)
    val highColor = Color(0xFF4527A0)

    Column(modifier = modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            dayLabels.forEach { d ->
                Text(
                    d,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.width(32.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp
                )
            }
        }
        Spacer(Modifier.height(4.dp))

        val offset = 1
        val total = offset + days.size
        val rows = (total + 6) / 7

        for (row in 0 until rows) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                for (col in 0 until 7) {
                    val cellIdx = row * 7 + col
                    val dayIdx = cellIdx - offset
                    val fraction = if (dayIdx < 0 || dayIdx >= days.size) null else days[dayIdx]

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (fraction == null) Color.Transparent
                                else androidx.compose.ui.graphics.lerp(
                                    lowColor,
                                    highColor,
                                    fraction
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (fraction != null) {
                            Text(
                                text = (dayIdx + 1).toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (fraction > 0.6f) Color.White else Color(0xFF4527A0),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(3.dp))
        }

        Spacer(Modifier.height(6.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                "Low",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Box(
                modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp))
                    .background(Brush.horizontalGradient(listOf(lowColor, highColor)))
            )
            Text(
                "High",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
