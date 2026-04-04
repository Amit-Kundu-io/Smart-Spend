package com.amit_kindu_io.bottomNavigation

import com.amit_kundu_io.analytics.presentation.navigation.AnalyticsRoutes
import com.amit_kundu_io.home.presentation.navigation.HomeRoutes
import com.amit_kundu_io.transactions.presentation.navigation.TransactionsRoutes
import common.resources.Res
import common.resources.ic_home
import common.resources.ic_more
import common.resources.ic_plus
import common.resources.ic_settings
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem(
    val route: String?,
    val label: String,
    val selectedIcon: DrawableResource,
) {

    data object Home : BottomNavItem(
        route = HomeRoutes.HomeGraph::class.qualifiedName,
        label = "Home",
        selectedIcon = Res.drawable.ic_home,
    )

    data object Transactions : BottomNavItem(
        route = TransactionsRoutes.TransactionsGraph::class.qualifiedName,
        label = "Transactions",
        selectedIcon = Res.drawable.ic_more,
    )
    data object Analytics : BottomNavItem(
        route = AnalyticsRoutes.AnalyticsGraph::class.qualifiedName,
        label = "Analytics",
        selectedIcon = Res.drawable.ic_home,
    )

    data object Settings : BottomNavItem(
        route = HomeRoutes.HomeGraph::class.qualifiedName,
        label = "Settings",
        selectedIcon = Res.drawable.ic_settings,
    )


}


val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Transactions,
    BottomNavItem.Analytics,
    BottomNavItem.Settings,
    )


// To detect when to show bottom nav
val bottomNavRoutes = bottomNavItems.map { it.route }.toSet()


