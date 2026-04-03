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
import androidx.navigation.toRoute
import com.amit_kundu_io.home.presentation.add_transaction_screen.AddTransactionRootScreen
import com.amit_kundu_io.home.presentation.home_screen.HomeRootScreen
import com.amit_kundu_io.home.presentation.transaction_detail.TransactionDetailRootScreen

fun NavGraphBuilder.homeNavigation(
    navController: NavController
) {
    navigation<HomeRoutes.HomeGraph>(startDestination = HomeRoutes.HomeRoute) {
        composable<HomeRoutes.AddTransactionRoute> {
            val backData: HomeRoutes.AddTransactionRoute = it.toRoute()
            AddTransactionRootScreen(
                transactionId = backData.id,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<HomeRoutes.TransactionDetailRoute> {
            val backData: HomeRoutes.TransactionDetailRoute = it.toRoute()
            TransactionDetailRootScreen(
                backData.id,
                onBack = {
                    navController.navigateUp()
                },
                onEdit = {id ->
                    navController.navigate(HomeRoutes.AddTransactionRoute(id))
                }
            )
        }
        composable<HomeRoutes.HomeRoute> {
            HomeRootScreen(
                navigateToAddTransaction = {
                    navController.navigate(HomeRoutes.AddTransactionRoute(null))
                },
                navigateTODetailsScreen = {
                    navController.navigate(HomeRoutes.TransactionDetailRoute(it))
                }
            )
        }
    }
}