/**
 * DeleteConfirmDialog.kt
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

package com.amit_kundu_io.theme.components.DeleteConfirmDialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amit_kundu_io.theme.Transaction
import com.amit_kundu_io.theme.ui.ExpenseRed
import com.amit_kundu_io.theme.ui.GradientEnd
import com.amit_kundu_io.theme.ui.IncomeGreen
import com.amit_kundu_io.theme.ui.lightGreen
import com.amit_kundu_io.utilities.Data_Models.Category
import com.amit_kundu_io.utilities.Data_Models.PaymentMethod
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.global_utility.GlobalUtility


@Composable
fun DeleteConfirmDialog(
    transaction: Transaction,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        icon = {
            // Red icon container
            Surface(
                color = GradientEnd.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(64.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        title = {
            Text(
                "Delete transaction?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "This will permanently remove this entry from your history and cannot be undone.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
                Spacer(Modifier.height(14.dp))
                // Transaction preview card
                Surface(
                    color = GradientEnd.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, GradientEnd.copy(alpha = .4f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                           .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            color = transaction.categoryColor,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(transaction.categoryEmoji, fontSize = 18.sp)
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                transaction.title,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "${transaction.date} · ${transaction.category}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            if (transaction.type?.value == TransactionType.INCOME.value) "+₹${
                                GlobalUtility.formatCurrency(
                                    transaction.amount
                                )
                            }"
                            else "-₹${GlobalUtility.formatCurrency(transaction.amount)}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (transaction.type?.value == TransactionType.INCOME.value) IncomeGreen else ExpenseRed
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Delete, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(6.dp))
                Text(
                    "Yes, delete it", style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 18.sp,
                        lineHeight = 20.sp
                    )
                )
            }
        },
        dismissButton = {
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    "Cancel, keep it",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 18.sp,
                        lineHeight = 20.sp
                    )
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DeleteConfirmDialogPreview() {
    val sampleTransaction = Transaction(
        id = "1",
        title = "Dinner at Restaurant",
        category = Category.FOOD,
        categoryEmoji = "🍽️",
        categoryColor = Color.Red,
        amount = 120.0,
        type = TransactionType.EXPENSE,
        date = "2026-04-03",
        time = "20:15",
        paymentMethod = PaymentMethod.CARD,
        note = "Family dinner"
    )

    DeleteConfirmDialog(
        transaction = sampleTransaction,
        onConfirm = {},
        onDismiss = {}
    )
}

