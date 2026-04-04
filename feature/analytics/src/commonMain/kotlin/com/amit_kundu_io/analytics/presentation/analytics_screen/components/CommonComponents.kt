/**
 * CommonComponents.kt
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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amit_kundu_io.utilities.global_utility.GlobalUtility

@Composable
fun CategoryBreakdownCard(categories: List<CategoryData>) {
    AnalyticsCard(title = "Category Breakdown") {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            categories.forEach { cat ->
                val animP by animateFloatAsState(
                    targetValue = cat.percentage,
                    animationSpec = tween(700),
                    label = "cat_${cat.name}"
                )
                Column {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("${cat.emoji} ${cat.name}", style = MaterialTheme.typography.bodySmall)
                        Text(
                            "₹${formatAmount(cat.amount)} · ${(cat.percentage * 100).toInt()}%",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(Modifier.height(5.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth().height(7.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(animP).fillMaxHeight()
                                .clip(RoundedCornerShape(7.dp)).background(cat.color)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatsGrid(stats: List<Pair<String, String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        stats.chunked(2).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { (label, value) ->
                    Surface(
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                label.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                letterSpacing = 0.4.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                value,
                                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun MonthSummaryRow(month: MonthSummary) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                month.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                month.subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                "₹${formatAmount(month.amount)}", style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(3.dp))
            TrendBadge(
                value = "${if (month.changePercent >= 0) "+" else ""}${month.changePercent}%",
                isPositive = month.changePercent > 0
            )
        }
    }
}

@Composable
fun QuarterCard(
    label: String,
    subtitle: String,
    amount: Long?,
    isCurrent: Boolean,
    isFaded: Boolean,
    modifier: Modifier
) {
    val bg = when {
        isCurrent -> Color(0xFF4527A0)
        isFaded -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    Surface(color = bg, shape = RoundedCornerShape(12.dp), modifier = modifier) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold,
                color = if (isCurrent) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            Text(
                if (amount != null) "₹${formatAmount(amount)}" else "—",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = if (isCurrent) Color.White else if (isFaded) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(2.dp))
            Text(
                subtitle, style = MaterialTheme.typography.labelSmall,
                color = if (isCurrent) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center, fontSize = 9.sp
            )
        }
    }
}

@Composable
fun TrendBadge(value: String, isPositive: Boolean?) {
    val (bg, fg) = when (isPositive) {
        true -> Color(0xFFFFDAD6) to MaterialTheme.colorScheme.error
        false -> Color(0xFFC8E6C9) to Color(0xFF1B5E20)
        null -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Surface(color = bg, shape = RoundedCornerShape(6.dp)) {
        Text(
            value, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = fg
        )
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(2.dp)).background(color))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun AnalyticsCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp, 14.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

fun formatAmount(amount: Long): String {
    return GlobalUtility.formatCurrency(amount.toDouble())
}
