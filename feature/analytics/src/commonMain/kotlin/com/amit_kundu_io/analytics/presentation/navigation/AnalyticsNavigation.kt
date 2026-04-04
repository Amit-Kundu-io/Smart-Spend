/**
 * AnalyticsNavigation.kt
 *
 * Author      : Amit Kundu
 * Created On  : 04/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.analytics.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.amit_kundu_io.analytics.presentation.analytics_screen.AnalyticsRootScreen

fun NavGraphBuilder.analyticsNavigation(navController: NavHostController) {
    navigation<AnalyticsRoutes.AnalyticsGraph>(startDestination = AnalyticsRoutes.AnalyticsRoute) {
        composable<AnalyticsRoutes.AnalyticsRoute>() {
            AnalyticsRootScreen()
        }
    }
}