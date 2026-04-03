/**
 * ThemeUtility.kt
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

package com.amit_kundu_io.theme.utility

import androidx.compose.ui.graphics.Color
import com.amit_kundu_io.theme.ui.CategoryFood
import com.amit_kundu_io.theme.ui.CategoryHealth
import com.amit_kundu_io.theme.ui.CategoryTransport
import com.amit_kundu_io.theme.ui.CategoryUtil
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.SuccessContainer

object ThemeUtility {
    fun mapCategory(category: String?): Pair<String, Color> {
        return when (category) {
            "Food" -> "🍔" to CategoryFood
            "Transport" -> "🚗" to CategoryTransport
            "Health" -> "💊" to CategoryHealth
            "Utilities" -> "⚡" to CategoryUtil
            "Income" -> "💰" to SuccessContainer
            else -> "📦" to GradientStart
        }
    }
}