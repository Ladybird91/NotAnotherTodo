package com.hw.notanothertodo

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material.icons.outlined.PriorityHigh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hw.notanothertodo.objects.Category
import com.hw.notanothertodo.objects.Task
import com.hw.notanothertodo.objects.UserViewModel
import com.hw.notanothertodo.objects.calculatePoints
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskScreen(contentPadding: PaddingValues = PaddingValues(), viewModel: UserViewModel = viewModel()) {
    val db = Firebase.firestore

    var categories = viewModel.user.categories
    val tasks = remember {
        viewModel.user.currentTasks
    }

    // State to track if the category dropdown menu is open
    var expanded by remember { mutableStateOf(false) }
    var showTaskBottomSheet by remember { mutableStateOf(false) }


    // State to track if what categories are Switched on or off
    val categoryCheckedState = remember {
        mutableStateMapOf<Category, Boolean>().apply {
            categories.forEach { category ->
                this[category] = true
            }
        }
    }

    // State to track the Checkboxes checked state for tasks
    val checkboxCheckedState = remember {
        mutableStateMapOf<Task, Boolean>().apply {
            tasks.forEach { task ->
                this[task] = task.checked
            }
        }
    }

    // State to track the selected categories
    var selectedCategories by remember { mutableStateOf(emptyList<Category>()) }

    // // State to track the Checkboxes checked state for points animation
    val animatePoints = remember {
        mutableStateMapOf<Task, Boolean>().apply {
            tasks.forEach { task ->
                this[task] = task.checked
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Category Button with dropdown menu
            Box {
                ElevatedButton(
                    onClick = { expanded = true },
                    elevation = ButtonDefaults.elevatedButtonElevation(5.dp) )
                {
                    Text("Category")
                }
                CustomDropdownMenu(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    categories = categories,
                    categoryCheckedState = categoryCheckedState,
                    onCategoryClick = { category ->
                        val isChecked = categoryCheckedState[category] ?: false
                        categoryCheckedState[category] = !isChecked
                        selectedCategories = if (isChecked) {
                            selectedCategories - category
                        } else {
                            selectedCategories + category
                        }
                    }
                )
            }
            // Priority Button - Needs to be implemented to sort Priority ratings
            ElevatedButton(
                onClick = { /* Handle button click */ },
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp)
            ) {
                Text("Priority")
            }
            // Difficulty Button - Needs to be implemented to sort Difficulty ratings
            ElevatedButton(
                onClick = { /* Handle button click */ },
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp)
            ) {
                Text("Difficulty")
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                //modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                val groupedTasks = tasks.groupBy { it.category }
                // Shows categories on screen that haven't been switched off
                categories.forEach { category ->
                    if (categoryCheckedState[category] == true) {
                        // Sticky header for each category
                        stickyHeader {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color.LightGray.copy(alpha = 0.4f),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = category.name,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                        // List of tasks under each category
                        items(groupedTasks[category] ?: emptyList()) { task ->
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
                                    // Color coded based on difficulty Checkbox for task
                                    Checkbox(
                                        checked = checkboxCheckedState[task] ?: false,
                                        onCheckedChange = { isChecked ->
                                            checkboxCheckedState[task] = isChecked
                                            task.checked = isChecked
                                            if (isChecked) {
                                                viewModel.user.addPoints(task.points)
                                            } else {
                                                viewModel.user.minusPoints(task.points)
                                            }
                                            animatePoints[task] = isChecked
                                        },
                                        colors = CheckboxDefaults.colors(
                                            uncheckedColor = getDifficultyColor(task.difficulty),
                                            checkedColor = getDifficultyColor(task.difficulty)
                                        )
                                    )
                                    // Task name
                                    Text(
                                        text = task.name,
                                        modifier = Modifier.padding(start = 1.dp)
                                    )
                                    PriorityIcons(task.priority)
                                    AnimatedVisibility(
                                        visible = animatePoints[task] ?: false,
                                        enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                                        exit = fadeOut(animationSpec = tween(durationMillis = 1000))
                                    ) {
                                        Text(
                                            text = "+" + task.points.toString(),
                                            color = Color.DarkGray,
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Custom floating action button for adding tasks
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                TaskFloatingActionButton(
                    onClick = { showTaskBottomSheet = true }
                )
            }
        }

        if (showTaskBottomSheet) {
            TaskBottomSheet(
                showTaskBottomSheet = showTaskBottomSheet,
                categories = categories,
                onClose = { showTaskBottomSheet = false },
                onTaskSave = { newTask ->
                    tasks.add(newTask)
                    showTaskBottomSheet = false
                    Log.d(
                        "NewTask",
                        "Name: ${newTask.name}, Category: ${newTask.category}, Priority: ${newTask.priority}, Difficulty: ${newTask.difficulty}"
                    )
                }
            )
        }
    }
}

// Dropdown menu for selecting categories
@Composable
fun CustomDropdownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    categories: List<Category>,
    categoryCheckedState: MutableMap<Category, Boolean>,
    onCategoryClick: (Category) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        modifier = Modifier.requiredSizeIn(maxHeight = 300.dp),
        onDismissRequest = { onExpandedChange(false) }
    ) {
        categories.forEach { category ->
            DropdownMenuItem(
                text = { Text(category.name) },
                trailingIcon = {
                    Switch(
                        modifier = Modifier
                            .semantics {
                                contentDescription = "Switch to choose which categories to view"
                            },
                        checked = categoryCheckedState[category] ?: false,
                        onCheckedChange = { isChecked ->
                            categoryCheckedState[category] = isChecked
                        }
                    )
                },
                onClick = { onCategoryClick(category) },
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskBottomSheet(
    showTaskBottomSheet: Boolean,
    categories: List<Category>,
    onClose: () -> Unit,
    onTaskSave: (Task) -> Unit
) {
    var isDifficultyMenuExpanded by remember { mutableStateOf(false) }
    var isPriorityMenuExpanded by remember { mutableStateOf(false) }
    var isCategoryMenuExpanded by remember { mutableStateOf(false) }

    val difficultyOptions = listOf("Easy", "Moderate", "Hard")
    var selectedDifficulty by remember { mutableStateOf("") }
    val priorityOptions = listOf("Low", "Medium", "High")
    var selectedPriority by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(Category("")) }
    var taskName by remember { mutableStateOf("") }

    if (showTaskBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onClose,
            sheetState = rememberModalBottomSheetState()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize()
                ) {
                    // User input field
                    TextField(
                        value = taskName,
                        onValueChange = { taskName = it },
                        label = { Text("Input new task here") }
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    // Category button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column {
                            Box {
                                ElevatedButton(
                                    onClick = { isCategoryMenuExpanded = true },
                                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                                ) {
                                    Text(selectedCategory.name.ifBlank { "Category" })
                                }
                            }

                            DropdownMenu(
                                expanded = isCategoryMenuExpanded,
                                onDismissRequest = { isCategoryMenuExpanded = false },
                                modifier = Modifier.requiredSizeIn(maxHeight = 160.dp)
                            ) {
                                categories.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(category.name) },
                                        onClick = {
                                            selectedCategory = category
                                            isCategoryMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }


                        // Difficulty button
                        Column {
                            Box {
                                ElevatedButton(
                                    onClick = { isDifficultyMenuExpanded = true },
                                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                                ) {
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
                            Box {
                                ElevatedButton(
                                    onClick = { isPriorityMenuExpanded = true },
                                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                                ) {
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
                                    val newTask = Task(
                                        name = taskName,
                                        category = selectedCategory,
                                        priority = selectedPriority,
                                        difficulty = selectedDifficulty
                                    )

                                    val points = newTask.calculatePoints()
                                    newTask.points = points

                                    onTaskSave(newTask)
                                    onClose()
                                }
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
    }
}


// Custom floating action button for adding tasks
@Composable
fun TaskFloatingActionButton(
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

// Get the corresponding color for a task difficulty level
@Composable
fun getDifficultyColor(difficulty: String): Color {
    return when (difficulty) {
        "Easy" -> Color.Green
        "Moderate" -> Color.Blue
        "Hard" -> Color.Red
        else -> Color.Gray
    }
}

// IF priority icon is displayed and icon color
@Composable
fun PriorityIcons(priority: String) {
    val iconTint = when (priority) {
        "Low" -> null // No icon for Low priority
        "Medium" -> Color.Blue // Blue tint for Medium priority
        "High" -> Color.Red // Red tint for High priority
        else -> null
    }

    if (priority != "Low") {
        if (iconTint != null) {
            Icon(
                imageVector = Icons.Outlined.PriorityHigh,
                contentDescription = "Priority High",
                modifier = Modifier.padding(start = 0.dp, end = 0.dp),
                tint = iconTint
            )
        }
    }
}







