/**
 * TransactionDetailScreen.kt
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

package com.amit_kundu_io.home.presentation.transaction_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.home.presentation.transaction_detail.components.DetailRow
import com.amit_kundu_io.theme.components.DeleteConfirmDialog.DeleteConfirmDialog
import com.amit_kundu_io.theme.ui.GradientEnd
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.SmartSpendTheme
import com.amit_kundu_io.theme.ui.Success
import com.amit_kundu_io.theme.ui.SuccessContainer
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.global_utility.GlobalUtility
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransactionDetailRootScreen(
    id: String,
    onBack: () -> Unit = {},
    onEdit: (String) -> Unit = {},
    viewModel: TransactionDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.onAction(TransactionDetailAction.ObserveTransaction(id))
    }

    TransactionDetailScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = onBack,
        onEdit ={
            onEdit(id)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionDetailScreen(
    state: TransactionDetailState,
    onAction: (TransactionDetailAction) -> Unit,
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {

    // In a real app, load from ViewModel/repository by txId

    var showDeleteDialog by remember { mutableStateOf(false) }

    // Gradient color based on transaction type
    val heroGradient = if (state.data?.type?.value == TransactionType.INCOME.value)
        Brush.linearGradient(listOf(Color(0xFF2E7D32), Color(0xFF4CAF50)))
    else
        Brush.linearGradient(listOf(GradientStart, GradientEnd))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(
                        onClick = { }
                    ) {
                        Icon(Icons.Default.Edit, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Edit")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GradientStart,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {

            // ── Hero section ─────────────────────────────────
            Box(
                modifier = Modifier.fillMaxWidth().background(brush = heroGradient).padding(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Icon
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.size(72.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(state.data?.categoryEmoji ?: "-", fontSize = 36.sp)
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    // Amount
                    Text(
                        text = if (state.data?.type?.value == TransactionType.INCOME.value) "+₹${
                            GlobalUtility.formatCurrency(
                                state.data.amount
                            )
                        }"
                        else "-₹${GlobalUtility.formatCurrency(state.data?.amount ?: 0.0)}",
                        style = MaterialTheme.typography.displayLarge.copy(fontSize = 40.sp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    // Title
                    Text(
                        text = state.data?.title ?: "-",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }

            // ── Detail body ───────────────────────────────────
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    // Detail card
                    ElevatedCard(shape = RoundedCornerShape(16.dp)) {
                        Column {
                            DetailRow(key = "Category") {
                                AssistChip(
                                    onClick = {},
                                    label = { Text("${state.data?.categoryEmoji} ${state.data?.category}") },
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            DetailRow(key = "Type") {
                                Surface(
                                    color = if (state.data?.type?.value == TransactionType.INCOME.value) SuccessContainer else MaterialTheme.colorScheme.errorContainer,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        if (state.data?.type?.value == TransactionType.INCOME.value) "Income" else "Expense",
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 3.dp
                                        ),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (state.data?.type?.value == TransactionType.INCOME.value) Success else MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            DetailRow(key = "Date", value = state.data?.date)
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            DetailRow(key = "Time", value = state.data?.time)
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            DetailRow(key = "Payment") {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        Icons.Outlined.CreditCard,
                                        null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        state.data?.paymentMethod?.label ?: "-",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            DetailRow(key = "Note", value = state.data?.note ?: "-")
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            DetailRow(key = "Transaction ID", value = state.data?.id)
                        }
                    }
                }


                // Action buttons
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Edit
                        Button(
                            onClick = onEdit,
                            modifier = Modifier.weight(1f).height(52.dp),
                            shape = RoundedCornerShape(50.dp)
                        ) {
                            Icon(Icons.Default.Edit, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Edit", style = MaterialTheme.typography.titleSmall)
                        }
                        // Delete
                        OutlinedButton(
                            onClick = { showDeleteDialog = true },
                            modifier = Modifier.weight(1f).height(52.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.error)
                        ) {
                            Icon(Icons.Default.Delete, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Delete", style = MaterialTheme.typography.titleSmall)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }

        // Delete confirmation dialog
        if (showDeleteDialog) {
            state.data?.let {
                DeleteConfirmDialog(
                    transaction = it,
                    onConfirm = {
                        showDeleteDialog = false

                    },
                    onDismiss = { showDeleteDialog = false }
                )
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    SmartSpendTheme {
        TransactionDetailScreen(
            state = TransactionDetailState(),
            onAction = {},
            onBack = {},
            onEdit = {}
        )
    }
}