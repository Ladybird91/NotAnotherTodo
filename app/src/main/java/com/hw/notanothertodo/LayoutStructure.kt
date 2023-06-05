package com.hw.notanothertodo

import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomLayoutStructure() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerItems = navigationDrawerMenu()
    val sheetState = rememberModalBottomSheetState()
    var showPrizeBottomSheet by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val barItems = navigationBarMenu()

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        CustomNavigationDrawer(navController, currentDestination, drawerItems) {}
    }) {
        Scaffold(
            topBar = {
                CustomTopBar {
                    scope.launch {
                        drawerState.open()
                    }
                }
            },
            bottomBar = {
                CustomNavigationBar(navController, currentDestination, barItems) {}
            },
        ) { innerPadding ->
            CustomNavHost(navController, innerPadding)
        }
    }

    if (showPrizeBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showPrizeBottomSheet = false
            }, sheetState = sheetState
        ) {
            Button(onClick = {
                showPrizeBottomSheet = false
            }) {
                Text("Hide bottom sheet")
            }
        }
    }
}




