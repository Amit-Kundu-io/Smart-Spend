/**
 * GradientHeader.kt
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

package com.amit_kundu_io.theme.components.GradientHeader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.theme.ui.GradientEnd
import com.amit_kundu_io.theme.ui.GradientStart

@Composable
fun GradientHeader(
    gradientColors: List<Color> = listOf(GradientStart, GradientEnd),
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = Brush.linearGradient(
                colors = gradientColors,
                start = Offset(0f, 0f),
                end   = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            ))
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Column(content = content)
    }
}