package com.hw.notanothertodo

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


fun navigationBarMenu(): List<Pair<ImageVector, String>> {
    return listOf(
        Icons.Outlined.CheckBox to "Tasks",
        Icons.Outlined.Redeem to "Prizes",
        Icons.Outlined.AccountBox to "User"
    )
}

fun navigationDrawerMenu(): List<Pair<ImageVector, String>> {
    return listOf(
        Icons.Outlined.Login to "Login",
        Icons.Outlined.Settings to "Settings")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigation() {

    val pagesWithFab = listOf("Tasks", "Prizes")
    val pagesForTasks = listOf("Tasks")
    var currentPage by remember { mutableStateOf(navigationBarMenu().firstOrNull()?.second ?: "") }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerItems = navigationDrawerMenu()
    var selectedPage by remember { mutableStateOf(-1) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }


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
                        selected = selectedPage == index,
                        onClick = {
                            selectedPage = index
                        })
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CustomTopBar {
                    Log.d("TAG", "Navigation drawer open on click")
                    scope.launch {
                        drawerState.open()
                    }
                }
            },
            bottomBar = {
                CustomNavigationBar {
                    currentPage = it
                }
            },
            floatingActionButton = {
                if (pagesWithFab.contains(currentPage)) {
                    CustomFloatingActionButton {
                        showBottomSheet = true
                    }
                }

            }
        ) {
            if (pagesForTasks.contains(currentPage)) {
                TaskPageLayout(contentPadding = it)
                }
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = currentPage,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                    Text("Hide bottom sheet")
                }
            }
        }
    }



@Composable
fun CustomNavigationBar(callback: (String) -> Unit) {

    var selectedPage by remember { mutableStateOf(0) }
    val barItems = navigationBarMenu()

    NavigationBar {
        barItems.forEachIndexed { index, data ->
            NavigationBarItem(
                selected = selectedPage == index,
                onClick = {
                    selectedPage = index
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




