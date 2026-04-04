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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.theme.components.TransactionRow.TransactionRow
import com.amit_kundu_io.theme.ui.GradientEnd
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.SmartSpendTheme
import com.amit_kundu_io.theme.ui.lightGreen
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import com.amit_kundu_io.utilities.Logger.Logger
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransactionsRootScreen(
    viewModel: TransactionsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()

    // Handle disposing of the ViewModel

    DisposableEffect(Unit) {
        onDispose {
            // Cleanup logic here
            viewModel.clear() // or call a custom cleanup method
        }
    }

    Column(
        Modifier.fillMaxSize()
            .background(GradientStart)
            .statusBarsPadding()
            .background(Color.White)
    ) {
        TransactionsScreen(
            state = state,
            onAction = viewModel::onAction, query = query
        )
    }


}

@Composable
private fun TransactionsScreen(
    state: TransactionsState,
    onAction: (TransactionsAction) -> Unit,
    query: String,
) {


    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(99) }
    val filters = listOf(99, TransactionType.INCOME.value, TransactionType.EXPENSE.value)

    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit){
        onAction(TransactionsAction.OnNextPage)

    }

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
            Column(modifier = Modifier.fillMaxWidth().background(GradientEnd).padding(16.dp)) {
                //CustomTopBar(title = "Transactions")
                Spacer(Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { onAction(TransactionsAction.OnQueryChange(it)) },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Search transactions...") },
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        shape = RoundedCornerShape(50.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(

                            //  Container (background)
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,

                            //  Border colors
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.LightGray,

                            // Optional (nice UX)
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape,
                        modifier = Modifier.size(42.dp)
                    ) {
                        IconButton(onClick = {}) { Icon(Icons.Default.FilterList, null) }
                    }
                }
                Spacer(Modifier.height(10.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filters) { filter ->
                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = {
                                selectedFilter = filter
                                onAction(TransactionsAction.OnRefresh(filter))
                            },
                            label = {
                                Text(
                                    TransactionType.fromValue(filter)?.name ?: "ALL",
                                    color = Color.White
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = lightGreen.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }
        }

        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 50.dp)
        ) {

            if (state.uiItems.isEmpty()){
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
                    }
                }
            }

            items(state.uiItems) { item ->

                when (item) {

                    is TransactionUiItem.Header -> {
                        Column {
                            Text(
                                text = item.title.uppercase(),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    is TransactionUiItem.Item -> {
                        TransactionRow(transaction = item.data.toUi())
                        Spacer(Modifier.height(7.dp))
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
            onAction = {},
            query = "",
        )
    }
}