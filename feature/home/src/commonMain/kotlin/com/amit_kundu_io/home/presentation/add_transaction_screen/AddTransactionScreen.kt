/**
 * AddTransactionScreen.kt
 *
 * Author      : Amit Kundu
 * Created On  : 03/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.home.presentation.add_transaction_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import com.amit_kundu_io.theme.components.GradientHeader.GradientHeader
import com.amit_kundu_io.theme.components.chip.CategoryChip.CategoryChip
import com.amit_kundu_io.theme.ui.GradientEnd
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.SmartSpendTheme
import com.amit_kundu_io.theme.ui.Success
import com.amit_kundu_io.utilities.Data_Models.Category
import com.amit_kundu_io.utilities.Data_Models.PaymentMethod
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.global_utility.GlobalUtility
import com.amit_kundu_io.utilities.global_utility.PlatformGlobulUtility
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddTransactionRootScreen(
    viewModel: AddTransactionViewModel = koinViewModel(),
    onBack: () -> Unit = {},
    transactionId: String? = null,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(transactionId) {
        transactionId?.let {
            viewModel.onAction(AddTransactionAction.LoadTransaction(it))
        }
    }

    LaunchedEffect(state.isAddSuccessfully) {
        if (state.isAddSuccessfully) {
            onBack.invoke()
        }
    }

    AddTransactionScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = onBack
    )
}

@Composable
private fun AddTransactionScreen(
    state: AddTransactionState,
    onAction: (AddTransactionAction) -> Unit,
    onBack: () -> Unit
) {
//    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
//    var amount by remember { mutableStateOf("") }
//    var title by remember { mutableStateOf("") }
//    var note by remember { mutableStateOf("") }
//    var selectedCategory by remember { mutableStateOf(100) }
//    var selectedPayment by remember { mutableStateOf(100) }

    val gradientColors = when (state.type) {
        TransactionType.EXPENSE -> listOf(
            GradientStart,
            GradientEnd
        )

        TransactionType.INCOME -> listOf(Success, Color(0xFF4CAF50))

    }

    Column(modifier = Modifier.fillMaxSize()) {
        GradientHeader(gradientColors = gradientColors) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Surface(color = Color.White.copy(alpha = 0.2f), shape = CircleShape) {
                        Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                        }
                    }
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    "${if (state.isEditMode) "Edit" else "Add"} Transaction",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(16.dp))
            // Type selector
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(
                    TransactionType.EXPENSE,
                    TransactionType.INCOME,
                ).forEach { type ->
                    val isSelected = state.type == type
                    OutlinedButton(
                        onClick = { onAction(AddTransactionAction.OnTypeChange(type)) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) Color.White.copy(
                                alpha = 0.2f
                            ) else Color.Transparent,
                            contentColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
                        ),
                        border = BorderStroke(
                            if (isSelected) 2.dp else 1.5.dp,
                            if (isSelected) Color.White else Color.White.copy(alpha = 0.35f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 10.dp)
                    ) {
                        Text(
                            type.name,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            // Amount display
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "AMOUNT (₹)",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.75f)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "₹${state.amount.ifEmpty { "0" }}",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column {
                    Text(
                        "Transaction Title",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = state.title,
                        onValueChange = { onAction(AddTransactionAction.OnTitleChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. Grocery Shopping") },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }
            }
            item {
                Column {
                    Text(
                        "Amount",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = state.amount,
                        onValueChange = { onAction(AddTransactionAction.OnAmountChange(it.filter { c -> c.isDigit() || c == '.' })) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("0.00") },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        prefix = { Text("₹") })
                }
            }
            if (state.type.value == TransactionType.EXPENSE.value) {
                item {
                    Column {
                        Text(
                            "Category",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Category.all.forEach { category ->
                                CategoryChip(
                                    emoji = category.icon,
                                    label = category.name,
                                    selected = state.category == category.id,
                                    onClick = {
                                        onAction(
                                            AddTransactionAction.OnCategoryChange(
                                                category.id
                                            )
                                        )
                                    })
                            }
                        }
                    }
                }
            }
            item {
                Column {
                    Text(
                        "Payment Method",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PaymentMethod.all.forEach { payment ->
                            CategoryChip(
                                emoji = payment.icon,
                                label = payment.name,
                                selected = state.paymentMethod == payment.value,
                                onClick = { onAction(AddTransactionAction.OnPaymentChange(payment.value)) })
                        }
                    }
                }
            }
            item {
                Column {
                    Text(
                        "Note (optional)",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = state.note,
                        onValueChange = { onAction(AddTransactionAction.OnNoteChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Add a note...") },
                        shape = RoundedCornerShape(12.dp),
                        minLines = 2
                    )
                }
            }
            item {
                Button(
                    onClick = {
                        onAction(AddTransactionAction.OnSave)
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Check, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Save Transaction", style = MaterialTheme.typography.titleSmall)
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SmartSpendTheme {
        AddTransactionScreen(
            state = AddTransactionState(),
            onAction = {},
            onBack = {}
        )
    }
}