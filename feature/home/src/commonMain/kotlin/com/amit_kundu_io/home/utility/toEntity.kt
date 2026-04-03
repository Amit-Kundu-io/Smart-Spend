/**
 * toEntity.kt
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

package com.amit_kundu_io.home.utility

import com.amit_kundu_io.database.data.database.TransactionEntity
import com.amit_kundu_io.theme.Transaction
import com.amit_kundu_io.utilities.global_utility.GlobalUtility

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        title = title,
        amount = amount,
        type = type?.value ?: 0,
        category = category,
        note = null,
        date = GlobalUtility.currentEpochSeconds(),
        paymentMethod = paymentMethod?.value ?: 0
    )
}