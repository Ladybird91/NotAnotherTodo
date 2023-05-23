package com.hw.notanothertodo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


enum class BottomNavScreens(val route: String, val icon: ImageVector, val title: String) {
    Tasks("tasks", Icons.Outlined.CheckBox, "Tasks"),
    Prizes("prizes", Icons.Outlined.Redeem, "Prizes"),
    User("user", Icons.Outlined.AccountBox, "User")
}


// Returns a list of pairs representing the lower navigation bar menu items
fun navigationBarMenu(): List<Triple<ImageVector, String, String>> {
    return BottomNavScreens.values().map { screen ->
        Triple(screen.icon, screen.title, screen.route)
    }
}

// Returns a list of pairs representing the side navigation drawer menu items
fun navigationDrawerMenu(): List<Pair<ImageVector, String>> {
    return listOf(
        Icons.Outlined.Login to "Login",
        Icons.Outlined.Settings to "Settings",
        Icons.Outlined.Info to "About"
    )
}

@Composable
fun CustomNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController,
        startDestination = BottomNavScreens.Tasks.route,
        Modifier.padding(innerPadding)
    ) {
        composable(BottomNavScreens.Tasks.route) { TaskScreen() }
        composable(BottomNavScreens.Prizes.route) { PrizeScreen() }
        composable(BottomNavScreens.User.route) { UserScreen() }
    }
}

@Composable
fun CustomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?,
    barItems: List<Triple<ImageVector, String, String>>,
    onItemClick: (String) -> Unit
) {
    NavigationBar {
        barItems.forEachIndexed { index, data ->
            val route = BottomNavScreens.values()[index].route
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    onItemClick(data.second)
                },
                label = {
                    Text(text = data.second)
                },
                icon = {
                    Icon(imageVector = data.first, contentDescription = "Navigation bar icons")
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(callback: () -> Unit) {
    TopAppBar(title = {}, navigationIcon = {
        IconButton(onClick = callback) {
            Icon(Icons.Filled.Menu, contentDescription = "Navigation Drawer")
        }
    })
}













