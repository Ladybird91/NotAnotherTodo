package com.hw.notanothertodo

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hw.notanothertodo.objects.Prize
import com.hw.notanothertodo.objects.User
import com.hw.notanothertodo.objects.calculateCost

@Composable
fun PrizeScreen(contentPadding: PaddingValues = PaddingValues()) {
    val testUser = User("jason", "jason@hotmail.com")
    testUser.startUpPrize()
    val prizes = remember {
        testUser.currentPrizes
    }
    val userPoints = testUser.getCurrentPoints()

    var showPrizeBottomSheet by remember { mutableStateOf(false) }

/*
    // State to track if what prizes are starred - in progress
    val prizesStaredState = remember {
        mutableStateMapOf<Prize, Boolean>().apply {
            prizes.forEach { prize ->
                this[prize] = prize.isStarred
            }
        }
    }*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // Box displaying userPoints
            Box(
                modifier = Modifier
                    .border(
                        3.dp,
                        Color(0xFF897A9E).copy(alpha = 0.3f),
                        shape = RoundedCornerShape(10.dp)
            )){
                Text(
                    text = "Current Points: $userPoints",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                //modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                // Shows categories on screen that haven't been switched off
                items(prizes) { prize ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .border(
                                1.dp,
                                Color.Gray.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = prize.title,
                                modifier = Modifier.padding(8.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            ElevatedButton(
                                onClick = { /* Handle button click here */ },
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCartCheckout,
                                        contentDescription = "Prize item checkout"
                                    )
                                    Text(prize.cost.toString())
                                }
                            }

                        }
                    }
                }
            }
            // Custom floating action button for adding prizes
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                PrizeFloatingActionButton(
                    onClick = { showPrizeBottomSheet = true }
                )
            }
        }

        if (showPrizeBottomSheet) {
            PrizeBottomSheet(
                padding = contentPadding,
                showTaskBottomSheet = showPrizeBottomSheet,
                onClose = { showPrizeBottomSheet = false },
                onPrizeSave = { newPrize ->
                    prizes.add(newPrize)
                    showPrizeBottomSheet = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrizeBottomSheet(
    padding: PaddingValues = PaddingValues(),
    showTaskBottomSheet: Boolean,
    onClose: () -> Unit,
    onPrizeSave: (Prize) -> Unit
) {
    var isCostMenuExpanded by remember { mutableStateOf(false) }
    val costRangeOptions = listOf("Cheap", "Affordable", "Mid-range", "Premium")
    var selectedCostRange by remember { mutableStateOf("") }

    var prizeName by remember { mutableStateOf("") }

    if (showTaskBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onClose,
            sheetState = rememberModalBottomSheetState()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize()
                ) {
                    // User input field
                    TextField(
                        value = prizeName,
                        onValueChange = { prizeName = it },
                        label = { Text("Input new prize here") }
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    // Cost range button
                    Column {
                        Box {
                            ElevatedButton(onClick = { isCostMenuExpanded = true }) {
                                Text(selectedCostRange.ifBlank { "Cost Range" })
                            }
                        }
                        DropdownMenu(
                            expanded = isCostMenuExpanded,
                            onDismissRequest = { isCostMenuExpanded = false }
                        ) {
                            costRangeOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        selectedCostRange = option
                                        isCostMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(180.dp))
                    // Icon in lower right corner
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    val newPrize = Prize(
                                        title = prizeName,
                                        prizeCostRange = selectedCostRange,
                                        isStarred = false,
                                    )

                                    val cost = newPrize.calculateCost()
                                    newPrize.cost = cost

                                    onPrizeSave(newPrize)
                                    onClose()
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SaveAs,
                                contentDescription = "Input Button to save new prize",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


// Custom floating action button for adding tasks
@Composable
fun PrizeFloatingActionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .size(75.dp)
            .padding(7.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "FAB Add Icon")
    }
}