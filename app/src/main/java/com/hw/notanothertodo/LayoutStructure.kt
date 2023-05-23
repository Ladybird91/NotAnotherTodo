package com.hw.notanothertodo


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    var selectedPage by remember { mutableStateOf(-1) }
    val sheetState = rememberModalBottomSheetState()
    var showPrizeBottomSheet by remember { mutableStateOf(false) }
    var showTaskBottomSheet by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val barItems = navigationBarMenu()

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        Box(
            Modifier.fillMaxSize()
        ) {
            Column {
                ModalDrawerSheet(Modifier.weight(1f)) {
                    drawerItems.forEachIndexed { index, data ->
                        NavigationDrawerItem(icon = {
                            Icon(imageVector = data.first, contentDescription = data.second)
                        }, label = {
                            Text(text = data.second)
                        }, selected = selectedPage == index, onClick = {
                            selectedPage = index
                            //navController.navigate(data.second)
                        })
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
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
        Scaffold(topBar = {
            CustomTopBar {
                scope.launch {
                    drawerState.open()
                }
            }
        }, bottomBar = {
            CustomNavigationBar(navController, currentDestination, barItems) {}
        }, floatingActionButton = {
            if (currentDestination?.route == BottomNavScreens.Tasks.route) {
                CustomFloatingActionButton {
                    // Open TaskBottomSheet
                    showTaskBottomSheet = true
                }
            } else if (currentDestination?.route == BottomNavScreens.Prizes.route) {
                CustomFloatingActionButton {
                    // Open PrizeBottomSheet
                    showPrizeBottomSheet = true
                }
            }
        }) { innerPadding ->
            CustomNavHost(navController, innerPadding)
        }
    }

    if (showTaskBottomSheet) {

        var isDifficultyMenuExpanded by remember { mutableStateOf(false) }
        var isPriorityMenuExpanded by remember { mutableStateOf(false) }
        var isCategoryMenuExpanded by remember { mutableStateOf(false) }

        val difficultyOptions = listOf("Low", "Medium", "High")
        var selectedDifficulty by remember { mutableStateOf("") }
        val priorityOptions = listOf("Low", "Medium", "High")
        var selectedPriority by remember { mutableStateOf("") }

        ModalBottomSheet(
            onDismissRequest = {
                showTaskBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    // User input field
                    TextField(
                        value = "", // Add a state variable for the input value
                        onValueChange = { /* Update the input value state variable */ },
                        label = { Text("Input new task here") }
                    )
                    Spacer(modifier = Modifier.height(40.dp))

                    // Buttons section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .offset(y = (-10).dp)
                                    .padding(bottom = 40.dp)
                            ) {
                                ElevatedButton(onClick = { isCategoryMenuExpanded = true }) {
                                    Text("Category")
                                }
                            }

                            DropdownMenu(
                                expanded = isCategoryMenuExpanded,
                                onDismissRequest = { isCategoryMenuExpanded = false }
                            ) {
                                // Transparent dropdown menu (height = 0)
                            }
                        }

                        // Difficulty button
                        Column {
                            Box(modifier = Modifier
                                .offset(y = (-10).dp)
                                .padding(bottom = 40.dp)
                            ) {
                                ElevatedButton(onClick = { isDifficultyMenuExpanded = true }) {
                                    Text(selectedDifficulty.ifBlank { "Difficulty" })
                                }
                            }

                            DropdownMenu(
                                expanded = isDifficultyMenuExpanded,
                                onDismissRequest = { isDifficultyMenuExpanded = false }
                            ) {
                                difficultyOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            selectedDifficulty = option
                                            isDifficultyMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Priority button
                        Column {
                            Box(modifier = Modifier
                                .offset(y = (-10).dp)
                                .padding(bottom = 40.dp)
                            ) {
                                ElevatedButton(onClick = { isPriorityMenuExpanded = true }) {
                                    Text(selectedPriority.ifBlank { "Priority" })
                                }
                            }

                            DropdownMenu(
                                expanded = isPriorityMenuExpanded,
                                onDismissRequest = { isPriorityMenuExpanded = false }
                            ) {
                                priorityOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            selectedPriority = option
                                            isPriorityMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(125.dp))
                    // Icon in lower right corner
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SaveAs,
                            contentDescription = "Input Button to save new task",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }


    if (showPrizeBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showPrizeBottomSheet = false
            }, sheetState = sheetState
        ) {
            // Content of PrizeBottomSheet
            Button(onClick = {
                showPrizeBottomSheet = false // Hide the bottom sheet
            }) {
                Text("Hide bottom sheet")
            }
        }
    }
}

// Custom floating action button for adding tasks
@Composable
fun CustomFloatingActionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(Icons.Filled.Add, contentDescription = "FAB Add Icon")
    }
}


