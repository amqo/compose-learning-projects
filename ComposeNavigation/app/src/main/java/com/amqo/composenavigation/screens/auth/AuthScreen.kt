package com.amqo.composenavigation.screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amqo.composenavigation.navigation.AuthNavGraph
import com.amqo.composenavigation.screens.auth.AuthScreenContent

@Composable
fun AuthScreen(
    navController: NavHostController = rememberNavController(),
    onNavigateBack: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        AuthNavGraph(
            onNavigateBack = onNavigateBack,
            navController = navController
        )
    }
}

@Composable
fun BottomBar(
  navController: NavHostController
) {
    val screens = listOf(
        AuthScreenContent.Login,
        AuthScreenContent.SigUp
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isDestinationCorrect = screens.any { destination -> destination.route == currentDestination?.route }
    if (isDestinationCorrect) {
        BottomNavigation {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: AuthScreenContent,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = "${screen.title} icon")
        },
        selected = currentDestination?.hierarchy?.any { destination ->
            destination.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}