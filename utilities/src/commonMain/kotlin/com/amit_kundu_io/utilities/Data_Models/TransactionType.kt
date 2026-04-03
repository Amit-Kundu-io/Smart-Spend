/**
 * TransactionType.kt
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

package com.amit_kundu_io.utilities.Data_Models

enum class TransactionType(val value: Int) {
    EXPENSE(100),
    INCOME(101);

    companion object {
        fun fromValue(value: Int?): TransactionType? {
            return values().find { it.value == value }
        }
    }
}
