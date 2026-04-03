/**
 * TransactionDetailState.kt
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

import androidx.compose.runtime.Immutable
import com.amit_kundu_io.database.data.database.entity.TransactionEntity
import com.amit_kundu_io.theme.Transaction

@Immutable
data class TransactionDetailState(
    val isLoading: Boolean = false,
    val data: Transaction? = null
)