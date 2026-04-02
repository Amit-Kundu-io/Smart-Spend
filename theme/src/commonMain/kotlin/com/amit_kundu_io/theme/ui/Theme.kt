/**
 * Theme.kt
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

package com.amit_kundu_io.theme.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val SpendWiseLightColorScheme = lightColorScheme(
    primary = Color(0xFF006874),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF97F0FF),
    onPrimaryContainer = Color(0xFF001F24),
    secondary = Color(0xFF4A6267),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCCE8ED),
    onSecondaryContainer = Color(0xFF051F23),
    tertiary = Color(0xFF525E7D),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDAE2FF),
    onTertiaryContainer = Color(0xFF0E1B37),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF5FAFB),
    surface = Color(0xFFF5FAFB),
    surfaceVariant = Color(0xFFDBE4E6),
    onSurface = Color(0xFF171D1E),
    onSurfaceVariant = Color(0xFF3F4849),
    outline = Color(0xFF6F797A),
    outlineVariant = Color(0xFFBFC8CA),
)

private val SpendWiseDarkColorScheme = darkColorScheme(
    primary = Color(0xFF4FD8EB),
    onPrimary = Color(0xFF00363D),
    primaryContainer = Color(0xFF004F57),
    onPrimaryContainer = Color(0xFF97F0FF),
    secondary = Color(0xFFB1CBD1),
    onSecondary = Color(0xFF1C3438),
    secondaryContainer = Color(0xFF334B4F),
    onSecondaryContainer = Color(0xFFCCE8ED),
    tertiary = Color(0xFFBAC6EA),
    onTertiary = Color(0xFF24304C),
    tertiaryContainer = Color(0xFF3B4764),
    onTertiaryContainer = Color(0xFFDAE2FF),
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF0F1516),
    surface = Color(0xFF0F1516),
    surfaceVariant = Color(0xFF3F4849),
    onSurface = Color(0xFFE6F2F3),
    onSurfaceVariant = Color(0xFFBFC8CA),
    outline = Color(0xFF899294),
    outlineVariant = Color(0xFF3F4849),
)

@Composable
fun SpendWiseTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) SpendWiseDarkColorScheme else SpendWiseLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = SpendWiseTypography,
        content = content
    )
}
