/**
 * TransactionsAction.kt
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

sealed interface TransactionsAction {
    object OnNextPage : TransactionsAction
    data class OnRefresh(val type: Int? = null) : TransactionsAction
    data class OnQueryChange(val query : String) : TransactionsAction

}