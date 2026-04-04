/**
 * CategorySpendingBar.kt
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

package com.amit_kundu_io.theme.components.Bar.CategorySpendingBar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.theme.CategorySpending
import com.amit_kundu_io.theme.ui.ExpenseRed
import com.amit_kundu_io.utilities.global_utility.GlobalUtility

@Composable
fun CategorySpendingBar(category: CategorySpending, modifier: Modifier = Modifier) {
    val animatedWidth by animateFloatAsState(
        targetValue = category.percentage / 100f,
        animationSpec = tween(800),
        label = "catbar"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("${category.emoji} ${category.name}", style = MaterialTheme.typography.bodySmall)
            Text(
                "₹${GlobalUtility.formatCurrency(category.amount)} · ${(category.percentage).toInt()}%",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = ExpenseRed
            )
        }
        Spacer(Modifier.height(5.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(animatedWidth).fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp)).background(category.color)
            )
        }
        Spacer(Modifier.height(10.dp))
    }
}