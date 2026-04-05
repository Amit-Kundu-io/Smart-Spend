/**
 * AddTransactionState.kt
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

import androidx.compose.runtime.Immutable
import com.amit_kundu_io.utilities.Data_Models.TransactionType
import kotlin.time.Clock

@Immutable
data class AddTransactionState(
    val isLoading: Boolean = false,
    val isAddSuccessfully: Boolean = false,


    val id: String? = null,
    val type: TransactionType = TransactionType.EXPENSE,
    val amount: String = "",
    val title: String = "",
    val date: Long = Clock.System.now().toEpochMilliseconds() / 1000,
    val note: String = "",
    val category: Int = 100,
    val paymentMethod: Int = 100,
    val isEditMode: Boolean = false,
)