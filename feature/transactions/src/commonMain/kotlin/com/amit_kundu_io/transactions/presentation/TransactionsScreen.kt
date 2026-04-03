/**
 * TransactionsScreen.kt
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

package com.amit_kundu_io.transactions.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.theme.Transaction
import com.amit_kundu_io.theme.components.TransactionRow.TransactionRow
import com.amit_kundu_io.theme.ui.CategoryFood
import com.amit_kundu_io.theme.ui.SmartSpendTheme
import com.amit_kundu_io.utilities.Data_Models.PaymentMethod
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.Logger.Logger
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransactionsRootScreen(
    viewModel: TransactionsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TransactionsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun TransactionsScreen(
    state: TransactionsState,
    onAction: (TransactionsAction) -> Unit,
) {


    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(99) }
    val filters = listOf(99, TransactionType.INCOME.value, TransactionType.EXPENSE.value)

    val lazyListState = rememberLazyListState()

    LaunchedEffect(state.uiItems) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == state.uiItems.lastIndex) {
                    Logger.d("ADADADADDA","call $lastVisibleIndex")
                    onAction(TransactionsAction.OnNextPage)
                }
            }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        // Search + Filter header
        Surface(tonalElevation = 2.dp) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    "Transactions",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))

                Spacer(Modifier.height(10.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filters) { filter ->
                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter },
                            label = { Text(TransactionType.fromValue(filter)?.name ?: "ALL") },
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }
            }
        }

        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 50.dp)
        ) {

            items(state.uiItems) { item ->

                when (item) {

                    is TransactionUiItem.Header -> {
                        Text(
                            text = item.title.uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    is TransactionUiItem.Item -> {
                        ElevatedCard(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                TransactionRow(transaction = item.data.toUi())
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }

            if (state.isLoadingMore) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SmartSpendTheme {
        TransactionsScreen(
            state = TransactionsState(),
            onAction = {}
        )
    }
}