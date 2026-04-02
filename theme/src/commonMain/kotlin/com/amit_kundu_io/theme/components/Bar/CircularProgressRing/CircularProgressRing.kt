/**
 * CircularProgressRing.kt
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

package com.amit_kundu_io.theme.components.Bar.CircularProgressRing

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.utilities.global_utility.GlobalUtility

@Composable
fun CircularProgressRing(
    progress: Float,
    currentAmount: Double,
    goalAmount: Double,
    strokeColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000),
        label = "ring"
    )
    Box(modifier = modifier.size(120.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeW = 12.dp.toPx()
            val radius = (size.minDimension - strokeW) / 2
            val center = Offset(size.width / 2, size.height / 2)
            // Track
            drawCircle(
                color = Color.White.copy(alpha = 0.2f),
                radius = radius,
                style = androidx.compose.ui.graphics.drawscope.Stroke(strokeW)
            )
            // Progress arc
            drawArc(
                color = strokeColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(strokeW, cap = Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "₹${GlobalUtility.formatCurrency(currentAmount)}",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                "of ₹${GlobalUtility.formatCurrency(goalAmount)}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}