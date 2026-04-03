/**
 * TransactionUiItem.kt
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

import com.amit_kundu_io.database.data.database.entity.TransactionEntity

sealed class TransactionUiItem {
    data class Header(val title: String) : TransactionUiItem()
    data class Item(val data: TransactionEntity) : TransactionUiItem()
}