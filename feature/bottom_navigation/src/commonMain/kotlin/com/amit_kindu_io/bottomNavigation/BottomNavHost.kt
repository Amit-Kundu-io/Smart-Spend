package com.amit_kindu_io.bottomNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.amit_kundu_io.analytics.presentation.navigation.analyticsNavigation
import com.amit_kundu_io.home.presentation.navigation.HomeRoutes
import com.amit_kundu_io.home.presentation.navigation.homeNavigation
import com.amit_kundu_io.transactions.presentation.navigation.transactionsNavigation


@Composable
fun BottomNavHost(
    navController: NavHostController,
    ) {

    NavHost(
        navController = navController,
        startDestination = HomeRoutes.HomeGraph,
    ) {
        homeNavigation(navController)
        transactionsNavigation(navController)
        analyticsNavigation(navController)
    }
}
