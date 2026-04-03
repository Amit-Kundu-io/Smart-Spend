/**
 * TransactionsViewModel.kt
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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import com.amit_kundu_io.database.domain.Repo.TransactionRepository
import com.amit_kundu_io.utilities.Logger.Logger
import com.amit_kundu_io.utilities.paginator.Paginator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

class TransactionsViewModel(
    private val repo: TransactionRepository

) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(TransactionsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TransactionsState()
        )

    private var currentPage = 0
    private val pageSize = 20


    fun onAction(action: TransactionsAction) {
        when (action) {
            TransactionsAction.OnNextPage -> {
                loadNextItems()
            }
        }
    }


    private val paginator by lazy {
        Logger.d("PAGINATION", "Paginator initialized")

        Paginator<Int, List<TransactionEntity>>(
            initialKey = 0,

            request = { currentPage ->
                Logger.d("PAGINATION", "Requesting page: $currentPage")

                val result = repo.getPage(
                    type = state.value.currentFilter,
                    page = currentPage,
                    pageSize = pageSize
                )

                Logger.d("PAGINATION", "Received ${result.size} items for page $currentPage")

                result
            },

            getNextKey = { currentPage, _ ->
                val next = currentPage + 1
                Logger.d("PAGINATION", "Next page will be: $next")
                next
            },

            isEndReached = { _, response ->
                val end = response.size < pageSize
                Logger.d("PAGINATION", "End reached: $end (size=${response.size})")
                end
            }
        )
    }

    init {
        Logger.d("PAGINATION", "ViewModel init → first load")
        loadNextItems()
    }

    fun loadNextItems(fullLoading: Boolean = false) {

        val current = _state.value

        Logger.d("PAGINATION", """
        loadNextItems called:
        isLoading=${current.isLoading}
        isLoadingMore=${current.isLoadingMore}
        endReached=${current.endReached}
        totalItems=${current.transactions.size}
    """.trimIndent())

        if (current.endReached || current.isLoadingMore || current.isLoading) {
            Logger.d("PAGINATION", "⛔ Blocked → no request")
            return
        }

        viewModelScope.launch {

            Logger.d("PAGINATION", "🚀 Starting pagination request")

            paginator.loadNext(

                onLoading = { loading ->
                    Logger.d("PAGINATION", "Loading state: $loading (fullLoading=$fullLoading)")

                    _state.update {
                        it.copy(
                            isLoadingMore = !fullLoading && loading,
                            isLoading = fullLoading && loading
                        )
                    }
                },

                onSuccess = { newItems ->

                    Logger.d("PAGINATION", "✅ Success → received ${newItems.size} items")

                    _state.update { currentState ->

                        val updatedList = (currentState.transactions + newItems)
                            .distinctBy { it.id }

                        Logger.d("PAGINATION", """
                        Updated list size: ${updatedList.size}
                        Previous size: ${currentState.transactions.size}
                    """.trimIndent())

                        currentState.copy(
                            transactions = updatedList,
                            uiItems = mapToUiItems(updatedList),
                            isLoadingMore = false,
                            isLoading = false,
                            error = "",
                            endReached = newItems.size < pageSize
                        )
                    }
                },

                onError = { throwable ->

                    Logger.e("PAGINATION", "❌ Error: ${throwable.message}", throwable)

                    _state.update {
                        it.copy(
                            isLoadingMore = false,
                            isLoading = false,
                            error = throwable.message ?: "Something went wrong"
                        )
                    }
                }
            )
        }
    }

    fun refresh(type: Int? = null) {
        currentPage = 0
        _state.update {
            it.copy(
                isLoading = true,
                transactions = emptyList(),
                currentFilter = type,
            )
        }
        loadNextItems(fullLoading = true)
    }


    fun mapToUiItems(
        transactions: List<TransactionEntity>
    ): List<TransactionUiItem> {

        val zone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()

        val today = now.toLocalDateTime(zone).date
        val yesterday = today.minus(1, DateTimeUnit.DAY)

        val sorted = transactions.sortedByDescending { it.date }

        val result = mutableListOf<TransactionUiItem>()

        var lastHeader: String? = null

        sorted.forEach { txn ->

            val txnDate = Instant
                .fromEpochMilliseconds(txn.date * 1000)
                .toLocalDateTime(zone)
                .date

            val header = when (txnDate) {
                today -> "Today"
                yesterday -> "Yesterday"
                else -> "${txnDate.month.name.take(3)} ${txnDate.dayOfMonth}"
            }

            // Add header only once
            if (header != lastHeader) {
                result.add(TransactionUiItem.Header(header))
                lastHeader = header
            }

            result.add(TransactionUiItem.Item(txn))
        }

        return result
    }
}