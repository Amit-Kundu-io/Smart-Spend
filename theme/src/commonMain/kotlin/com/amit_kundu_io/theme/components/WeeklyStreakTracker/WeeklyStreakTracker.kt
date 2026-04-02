/**
 * WeeklyStreakTracker.kt
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

package com.amit_kundu_io.theme.components.WeeklyStreakTracker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amit_kundu_io.theme.ui.Success
import com.amit_kundu_io.theme.ui.SuccessContainer

@Composable
fun WeeklyStreakTracker(
    days: List<Pair<String, DayStatus>>,
    amounts: List<String?>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        days.zip(amounts).forEach { (dayStatus, amount) ->
            val (day, status) = dayStatus
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            when (status) {
                                DayStatus.SUCCESS -> SuccessContainer
                                DayStatus.TODAY -> Color(0xFFBBDEFB)
                                DayStatus.FAILED -> MaterialTheme.colorScheme.errorContainer
                                DayStatus.FUTURE -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                        .then(
                            if (status == DayStatus.TODAY) Modifier.border(
                                2.dp,
                                Color(0xFF1976D2),
                                CircleShape
                            ) else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        when (status) {
                            DayStatus.SUCCESS -> "✅"
                            DayStatus.TODAY -> "📍"
                            DayStatus.FAILED -> "❌"
                            DayStatus.FUTURE -> "–"
                        },
                        fontSize = if (status == DayStatus.FUTURE) 14.sp else 16.sp
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    day,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    amount ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = when (status) {
                        DayStatus.SUCCESS -> Success
                        DayStatus.TODAY -> Color(0xFF1976D2)
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}
