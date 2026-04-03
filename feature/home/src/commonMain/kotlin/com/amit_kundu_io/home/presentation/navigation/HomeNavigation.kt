/**
 * HomeNavigation.kt
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

package com.amit_kundu_io.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.amit_kundu_io.home.presentation.add_transaction_screen.AddTransactionRootScreen
import com.amit_kundu_io.home.presentation.home_screen.HomeRootScreen

fun NavGraphBuilder.homeNavigation(
    navController: NavController
) {
    navigation<HomeRoutes.HomeGraph>(startDestination = HomeRoutes.HomeRoute) {
        composable<HomeRoutes.AddTransactionRoute> {
            AddTransactionRootScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<HomeRoutes.HomeRoute> {
            HomeRootScreen(
                navigateToAddTransaction = {
                    navController.navigate(HomeRoutes.AddTransactionRoute)
                }
            )
        }
    }
}