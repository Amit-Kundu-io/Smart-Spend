/**
 * TransactionDetailAction.kt
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

sealed interface TransactionDetailAction {
    data class ObserveTransaction(val id : String) : TransactionDetailAction

    data class OnDelete(val id : String) : TransactionDetailAction

}