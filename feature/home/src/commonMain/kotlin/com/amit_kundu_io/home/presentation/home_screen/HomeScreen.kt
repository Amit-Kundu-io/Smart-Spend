/**
 * HomeScreen.kt
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

package com.amit_kundu_io.home.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.home.presentation.home_screen.components.BudgetBottomSheet
import com.amit_kundu_io.home.utility.toUi
import com.amit_kundu_io.theme.components.GradientHeader.GradientHeader
import com.amit_kundu_io.theme.components.TransactionRow.TransactionRow
import com.amit_kundu_io.theme.components.cards.BalanceCard
import com.amit_kundu_io.theme.components.cards.BudgetProgressCard
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.SmartSpendTheme
import com.amit_kundu_io.theme.ui.lightGreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRootScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToAddTransaction: () -> Unit = {},
    navigateTODetailsScreen: (id: String) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showSheet by remember { mutableStateOf(false) }


    if (showSheet) {
        BudgetBottomSheet(
            state = state,
            onSave = {
                viewModel.saveBudget(
                    month = state.selectedMonth,
                    year = state.selectedYear,
                    amount = it
                )
                showSheet = false
            },
            onDismiss = { showSheet = false }
        )
    }
    Column (
        Modifier.fillMaxSize()
            .background(GradientStart)
            .statusBarsPadding()
            .background(Color.White)
    ){
        Box(modifier = Modifier.fillMaxSize()) {

            HomeScreen(
                state = state,
                onAction = viewModel::onAction,
                navigateTODetailsScreen = navigateTODetailsScreen,
                navigateToAddTransaction = {
                    if (state.idBudgetSet) {
                        navigateToAddTransaction.invoke()
                    } else {
                        showSheet = true
                    }
                }
            )

            //  Floating Add Button
            ExtendedFloatingActionButton(
                onClick = {
                    if (state.idBudgetSet) {
                        navigateToAddTransaction.invoke()
                    } else {
                        showSheet = true
                    }

                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .padding(bottom = 100.dp),
                containerColor = Color(0xFF0F5F5C)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Add Expense",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    )
                )
            }
        }

    }


}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onNavigate: (String) -> Unit = {},
    navigateTODetailsScreen: (id: String) -> Unit,
    navigateToAddTransaction: () -> Unit

) {

    Column(modifier = Modifier.fillMaxSize()) {
        // Gradient header
        GradientHeader {
            Spacer(Modifier.height(20.dp))
            BalanceCard(
                balance = state.balance,
                income = state.totalIncome,
                expense = state.totalExpense,
                streak = 5
            )
        }

        // Scrollable content
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 150.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                BudgetProgressCard(
                    spent = state.monthlySpent, budget = state.monthlyBudget,
                    remaining = state.remainingBudget,
                    progress = state.budgetUsedPercent,
                    date = state.monthLabel,
                )
            }
            // item { StatCardsGrid() }
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Recent Transactions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            if (state.recentTransactions.isNullOrEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .height(120.dp)
                            .border(1.dp, lightGreen, RoundedCornerShape(15.dp))
                            .background(
                                lightGreen.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(15.dp)
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "No Data Added",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                navigateToAddTransaction.invoke()
                            },
                            modifier = Modifier.height(35.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {

                            Icon(Icons.Default.Add, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Add Expense", style = MaterialTheme.typography.titleSmall)
                        }

                    }
                }
            } else {
                items(state.recentTransactions) { transaction ->
                    TransactionRow(transaction = transaction.toUi()) {
                        navigateTODetailsScreen(it)
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SmartSpendTheme {
        HomeScreen(
            state = HomeState(), onAction = {},
            navigateTODetailsScreen = {},
            navigateToAddTransaction = {}
            )
    }
}