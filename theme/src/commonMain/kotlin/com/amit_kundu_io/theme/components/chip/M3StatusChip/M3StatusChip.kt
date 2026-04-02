/**
 * M3StatusChip.kt
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

package com.amit_kundu_io.theme.components.chip.M3StatusChip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.theme.ui.Success
import com.amit_kundu_io.theme.ui.SuccessContainer
import com.amit_kundu_io.theme.ui.Warning
import com.amit_kundu_io.theme.ui.WarningContainer
import com.amit_kundu_io.utilities.Data_Models.ChipType

@Composable
fun M3StatusChip(label: String, chipType: ChipType = ChipType.PRIMARY) {
    val (bg, fg, border) = when (chipType) {
        ChipType.SUCCESS -> Triple(SuccessContainer, Success, Color(0xFFA5D6A7))
        ChipType.WARNING -> Triple(WarningContainer, Warning, Color(0xFFFFCC80))
        ChipType.ERROR -> Triple(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.error,
            Color(0xFFEF9A9A)
        )

        ChipType.PRIMARY -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.primary,
            Color(0xFF80DEEA)
        )

        ChipType.NEUTRAL -> Triple(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            MaterialTheme.colorScheme.outlineVariant
        )
    }
    Surface(
        color = bg,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.5.dp, border)
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            style = MaterialTheme.typography.labelMedium,
            color = fg
        )
    }
}