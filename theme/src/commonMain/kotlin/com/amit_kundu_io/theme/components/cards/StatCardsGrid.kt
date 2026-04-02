/**
 * StatCardsGrid.kt
 *
 * Author      : Amit Kundu
 * Created On  : 02/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.theme.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amit_kundu_io.theme.ui.ExpenseRed
import com.amit_kundu_io.theme.ui.IncomeGreen

@Composable
fun StatCardsGrid(modifier: Modifier = Modifier) {
    val stats = listOf(
        Triple("This Month", "₹22,150", ExpenseRed),
        Triple("Daily Avg", "₹716", MaterialTheme.colorScheme.onSurface),
        Triple("Savings", "₹42,850", IncomeGreen),
        Triple("Transactions", "47", MaterialTheme.colorScheme.onSurface),
    )
    Column(modifier = modifier) {
        for (row in stats.chunked(2)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { (label, value, color) ->
                    StatCard(
                        label = label,
                        value = value,
                        valueColor = color,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            if (row != stats.chunked(2).last()) Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun StatCard(label: String, value: String, valueColor: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
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
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                color = valueColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}