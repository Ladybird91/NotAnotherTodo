package com.hw.notanothertodo

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Returns a list of pairs representing the lower navigation bar menu items
fun navigationBarMenu(): List<Pair<ImageVector, String>> {
    return listOf(
        Icons.Outlined.CheckBox to "Tasks",
        Icons.Outlined.Redeem to "Prizes",
        Icons.Outlined.AccountBox to "User"
    )
}

// Returns a list of pairs representing the side navigation drawer menu items
fun navigationDrawerMenu(): List<Pair<ImageVector, String>> {
    return listOf(
        Icons.Outlined.Login to "Login",
        Icons.Outlined.Settings to "Settings"
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigation() {

    // To narrow down which pages show which features
    val pagesWithFab = listOf("Tasks", "Prizes")
    val pagesForTasks = listOf("Tasks")
    // Temporary to show selected page - need to implement real navigation later
    var currentPage by remember { mutableStateOf(navigationBarMenu().firstOrNull()?.second ?: "") }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerItems = navigationDrawerMenu()
    var selectedPage by remember { mutableStateOf(-1) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    // Modal side navigation drawer
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        Box(
            Modifier.fillMaxSize()
        ) {
            Column {
                ModalDrawerSheet(Modifier.weight(1f)) {
                    // Navigation drawer pages
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
                            }
                        )
                    }
                    // Decorative image for side drawer
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        // Image in drawer
                        Image(
                            painter = painterResource(R.drawable.passionflower_choice_two),
                            contentDescription = "Image in drawer",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }
    }) {
        // Main base layout on screens
        Scaffold(topBar = {
            // Custom top bar
            CustomTopBar {
                Log.d("TAG", "Navigation drawer open on click")
                scope.launch {
                    drawerState.open()
                }
            }
        }, bottomBar = {
            // Custom navigation bar
            CustomNavigationBar {
                currentPage = it
            }
        }, floatingActionButton = {
            if (pagesWithFab.contains(currentPage)) {
                CustomFloatingActionButton {
                    showBottomSheet = true
                }
            }
        }) {
            if (pagesForTasks.contains(currentPage)) {
                // Task page layout
                TaskPageLayout(contentPadding = it)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            // Display current page text
            Text(
                text = currentPage,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }
    }
    // Modal bottom sheet to add tasks or prizes demending on page
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





