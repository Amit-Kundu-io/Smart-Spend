/**
 * TransactionRow.kt
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

package com.amit_kundu_io.theme.components.TransactionRow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amit_kundu_io.theme.Transaction
import com.amit_kundu_io.theme.ui.ExpenseRed
import com.amit_kundu_io.theme.ui.IncomeGreen
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.global_utility.GlobalUtility

@Composable
fun TransactionRow(
    transaction: Transaction,
    onClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(transaction.id) })
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Category icon
        Surface(
            modifier = Modifier.size(44.dp),
            color = transaction.categoryColor,
            shape = RoundedCornerShape(14.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(transaction.categoryEmoji, fontSize = 20.sp)
            }
        }
        // Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                transaction.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                "${transaction.category.label} · ${transaction.date} ${transaction.time}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        // Amount
        Text(
            if (transaction.type?.value == TransactionType.INCOME.value) "+₹${
                GlobalUtility.formatCurrency(
                    transaction.amount
                )
            }" else "-₹${
                GlobalUtility.formatCurrency(
                    transaction.amount
                )
            }",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = if (transaction.type?.value == TransactionType.INCOME.value) IncomeGreen else ExpenseRed
        )
    }
}