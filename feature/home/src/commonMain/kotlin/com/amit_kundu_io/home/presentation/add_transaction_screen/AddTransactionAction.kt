/**
 * AddTransactionAction.kt
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

import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import com.amit_kundu_io.utilities.Data_Models.TransactionType

sealed interface AddTransactionAction {


    data class LoadTransaction(val id: String) : AddTransactionAction

    data class OnTypeChange(val type: TransactionType) : AddTransactionAction
    data class OnAmountChange(val value: String) : AddTransactionAction
    data class OnTitleChange(val value: String) : AddTransactionAction
    data class OnNoteChange(val value: String) : AddTransactionAction
    data class OnCategoryChange(val value: Int) : AddTransactionAction
    data class OnPaymentChange(val value: Int) : AddTransactionAction

    data class OnCurrentDateChange(val value: Long) : AddTransactionAction

    data object OnSave : AddTransactionAction

}