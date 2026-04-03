/**
 * TransactionsState.kt
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

import androidx.compose.runtime.Immutable
import com.amit_kundu_io.database.data.database.entity.TransactionEntity

@Immutable
data class TransactionsState(
    val transactions: List<TransactionEntity> = emptyList(),
    val uiItems: List<TransactionUiItem> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String = "",
    val currentFilter: Int? = null,
    val endReached: Boolean = false,
)