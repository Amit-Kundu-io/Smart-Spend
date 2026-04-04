package com.amit_kindu_io.bottomNavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amit_kundu_io.analytics.presentation.navigation.AnalyticsRoutes
import com.amit_kundu_io.home.presentation.navigation.HomeRoutes
import com.amit_kundu_io.transactions.presentation.navigation.TransactionsRoutes
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DashboardScreen() {
    val bottomNavController = rememberNavController()
    val currentRoute = bottomNavController.currentBackStackEntryAsState().value?.destination?.route

    var isShowBottomNav by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(currentRoute) {

        isShowBottomNav = when (currentRoute) {
            HomeRoutes.HomeRoute::class.qualifiedName -> {
                true
            }
            TransactionsRoutes.TransactionsRoute::class.qualifiedName -> {
                true
            }

            AnalyticsRoutes.AnalyticsRoute::class.qualifiedName -> {
                true
            }
            else -> {
                false
            }
        }
    }




    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
         //   .statusBarsPadding(),
        //.navigationBarsPadding(),
        bottomBar = {
            AnimatedVisibility(
                visible = isShowBottomNav,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = {
                        it / 2
                    }
                ),
                exit = fadeOut() + slideOutVertically(
                    targetOffsetY = {
                        it / 2
                    }
                )
            ) {
                AppBottomNav(
                    navController = bottomNavController,
                    items = bottomNavItems
                )
            }
        }

    ) { padding ->
        Box(
            modifier = Modifier.then(
                if (isShowBottomNav) Modifier.padding(padding)
                else Modifier
            )
        ) {
            BottomNavHost(
                bottomNavController,
            )
        }
    }
}


@Composable
fun AppBottomNav(
    navController: NavHostController,
    items: List<BottomNavItem>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val route = currentDestination?.route

    var isShowBottomNav by remember { mutableStateOf(true) }

    LaunchedEffect(route) {
        isShowBottomNav = when {
            route?.contains("attendance") == true -> false
            route?.contains("punch") == true -> false
            else -> true
        }
    }

    AnimatedVisibility(
        visible = isShowBottomNav,
        enter = fadeIn() + slideInVertically { it / 2 },
        exit = fadeOut() + slideOutVertically { it / 2 }
    ) {
        CustomBottomBar(
            items = items,
            currentDestination = currentDestination,
            onValueChange = { item ->
                navController.navigate(item.route ?: "") {
                    popUpTo(HomeRoutes.HomeRoute::class.qualifiedName ?: "") {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}


@Composable
fun CustomBottomBar(
    items: List<BottomNavItem>,
    currentDestination: NavDestination?,
    onValueChange: (BottomNavItem) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .clickable(onClick = {}, interactionSource = null, indication = null)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.TopCenter)
        ) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Gray.copy(alpha = .3f)),
                    startY = 0f,
                    endY = size.height
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 10.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { screen ->

                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(screen.selectedIcon),
                        contentDescription = screen.label,
                        tint = if (isSelected) Color.Black else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onValueChange(screen) }
                            ),
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = screen.label,
                        color = if (isSelected) Color.Black else Color.Gray,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(6.dp))

                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .background(Color.Black)
                        )
                    }
                }
            }
        }
    }
}


/*
@Composable
fun CustomBottomBar(
    items: List<BottomNavItem>,
    currentDestination: NavDestination?,
    onValueChange: (BottomNavItem) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp) // give space for floating button
    ) {

        //  Bottom Bar Background
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 16.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            items.forEachIndexed { index, screen ->

                // Skip center item (Add button)
                if (index == 2) {
                    Spacer(modifier = Modifier.weight(1f))
                    return@forEachIndexed
                }

                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onValueChange(screen) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(screen.selectedIcon),
                        contentDescription = screen.label,
                        tint = if (isSelected) Color.Black else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = screen.label,
                        fontSize = 11.sp,
                        color = if (isSelected) Color.Black else Color.Gray
                    )
                }
            }
        }

        // CENTER FLOATING BUTTON
        FloatingActionButton(
            onClick = {
                val addItem = items[2]
                onValueChange(addItem)
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp),
            containerColor = Color(0xFF0F5F5C),
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }
}


 */



