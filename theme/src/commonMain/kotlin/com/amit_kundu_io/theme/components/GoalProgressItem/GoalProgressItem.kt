/**
 * GoalProgressItem.kt
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

package com.amit_kundu_io.theme.components.GoalProgressItem

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.theme.components.chip.M3StatusChip.M3StatusChip
import com.amit_kundu_io.theme.ui.Success
import com.amit_kundu_io.theme.ui.Warning
import com.amit_kundu_io.utilities.Data_Models.ChipType

@Composable
fun GoalProgressItem(
    title: String,
    subtitle: String,
    progress: Float,
    chipType: ChipType,
    chipLabel: String,
    modifier: Modifier = Modifier
) {
    val animP by animateFloatAsState(targetValue = progress, animationSpec = tween(800), label = "goal")
    ElevatedCard(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Column {
                    Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Text(subtitle, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                M3StatusChip(chipLabel, chipType)
            }
            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { animP },
                modifier = Modifier.fillMaxWidth().height(10.dp).clip(RoundedCornerShape(8.dp)),
                color = when (chipType) {
                    ChipType.SUCCESS -> Success
                    ChipType.WARNING -> Warning
                    ChipType.ERROR   -> MaterialTheme.colorScheme.error
                    else             -> MaterialTheme.colorScheme.primary
                },
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}