/**
 * BalanceCard.kt
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.utilities.global_utility.GlobalUtility

@Composable
fun BalanceCard(
    balance: Double,
    income: Double,
    expense: Double,
    streak: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White.copy(alpha = 0.15f),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Total Balance",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                GlobalUtility.formatCurrency(balance),
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                BalanceStatItem(
                    "↑ INCOME",
                    GlobalUtility.formatCurrency(income),
                    Color(0xFFA5F3A0),
                    Modifier.weight(1f)
                )
                VerticalDivider(
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier
                        .height(36.dp)
                        .padding(horizontal = 12.dp)
                )
                BalanceStatItem(
                    "↓ EXPENSE",
                    GlobalUtility.formatCurrency(expense),
                    Color(0xFFFFCDD2),
                    Modifier.weight(1f)
                )
                VerticalDivider(
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier
                        .height(36.dp)
                        .padding(horizontal = 12.dp)
                )
                BalanceStatItem(
                    "🔥 STREAK",
                    "$streak Days",
                    Color(0xFFFFD54F),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BalanceStatItem(label: String, value: String, valueColor: Color, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(3.dp))
        Text(
            value,
            style = MaterialTheme.typography.titleMedium,
            color = valueColor,
            fontWeight = FontWeight.Bold
        )
    }
}