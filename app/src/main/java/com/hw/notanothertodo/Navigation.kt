package com.hw.notanothertodo

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.launch

fun navigationBarMenu(): List<Pair<ImageVector, String>> {
    return listOf(
        Icons.Outlined.CheckBox to "Tasks",
        Icons.Outlined.Redeem to "Prizes",
        Icons.Outlined.AccountBox to "User"
    )
}

fun navigationDrawerMenu(): List<Pair<ImageVector, String>> {
    return listOf(Icons.Outlined.Login to "Login", Icons.Outlined.Settings to "Settings")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigation() {

    var currentPage by remember { mutableStateOf(navigationBarMenu().firstOrNull()?.second) }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerItems = navigationDrawerMenu()
    var selectedItem by remember { mutableStateOf(-1) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                drawerItems.forEachIndexed { index, data ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(imageVector = data.first, contentDescription = data.second)
                        },
                        label = {
                            Text(text = data.second)
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                        })
                }
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                CustomNavigationBar {
                    currentPage = it
                }
            },
            topBar = {
                CustomTopBar {
                    Log.d("TAG", "Navigation drawer open on click")
                    scope.launch {
                        drawerState.open()
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$currentPage Page")
            }
        }
    }
}

@Composable
fun CustomNavigationBar(callback: (String) -> Unit) {

    var selectedIndex by remember { mutableStateOf(0) }
    val barItems = navigationBarMenu()

    NavigationBar {
        barItems.forEachIndexed { index, data ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    callback(data.second)
                },
                label = {
                    Text(text = data.second)
                },
                icon = {
                    Icon(imageVector = data.first, contentDescription = "Navigation bar icons")
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(callback: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = callback) {
                Icon(Icons.Filled.Menu, contentDescription = "Navigation Drawer")
            }
        }
    )
}