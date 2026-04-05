package com.amit_kindu_io.bottomNavigation

import com.amit_kundu_io.analytics.presentation.navigation.AnalyticsRoutes
import com.amit_kundu_io.home.presentation.navigation.HomeRoutes
import com.amit_kundu_io.transactions.presentation.navigation.TransactionsRoutes
import common.resources.Res
import common.resources.ic_home
import common.resources.ic_more
import common.resources.ic_plus
import common.resources.ic_settings
import common.resources.outline_analytics_24
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem<T : Any>(
    val route: T,
    val label: String,
    val selectedIcon: DrawableResource,
) {

    data object Home : BottomNavItem<HomeRoutes.HomeGraph>(
        route = HomeRoutes.HomeGraph,
        label = "Home",
        selectedIcon = Res.drawable.ic_home,
    )

    data object Transactions : BottomNavItem<TransactionsRoutes.TransactionsGraph>(
        route = TransactionsRoutes.TransactionsGraph,
        label = "Transactions",
        selectedIcon = Res.drawable.ic_more,
    )

    data object Analytics : BottomNavItem<AnalyticsRoutes.AnalyticsGraph>(
        route = AnalyticsRoutes.AnalyticsGraph,
        label = "Analytics",
        selectedIcon = Res.drawable.outline_analytics_24,
    )

    data object Settings : BottomNavItem<HomeRoutes.HomeGraph>(
        route = HomeRoutes.HomeGraph,
        label = "Settings",
        selectedIcon = Res.drawable.ic_settings,
    )
}


val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Transactions,
    BottomNavItem.Analytics,
    //BottomNavItem.Settings,
    )


// To detect when to show bottom nav
val bottomNavRoutes = bottomNavItems.map { it.route }.toSet()


