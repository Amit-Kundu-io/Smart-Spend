/**
 * GlobalUtility.kt
 *
 * Author      : Amit Kundu
 * Created On  : 02/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.utilities.global_utility

object GlobalUtility {
    fun formatCurrency(amount: Double): String {
        val rounded = kotlin.math.round(amount).toLong()
        val str = rounded.toString()

        return when {
            str.length <= 3 -> str
            else -> {
                val last3 = str.takeLast(3)
                val rest = str.dropLast(3)
                    .reversed()
                    .chunked(2)
                    .joinToString(",")
                    .reversed()
                "$rest,$last3"
            }
        }
    }
}