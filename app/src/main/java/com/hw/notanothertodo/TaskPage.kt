package com.hw.notanothertodo

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import com.hw.notanothertodo.objects.Category
import com.hw.notanothertodo.objects.Task
import com.hw.notanothertodo.objects.User




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskScreen(
    contentPadding: PaddingValues = PaddingValues()
) {
    val testUser: User = User("jason", "jason@hotmail.com")
    testUser.startUp()
    var categories = testUser.categories
//    // Temporary hard coded values for testing
//    val categories = listOf(
//        Category("Home"),
//        Category("Work"),
//        Category("Chores")
//    )

    // Temporary hard coded values for testing
//    val tasks = remember { mutableStateListOf(
//        Task("Task 1", categories[0], "High", "Easy", false),
//        Task("Task 2", categories[1], "Medium", "Medium", false),
//        Task("Task 3", categories[2], "Low", "Hard", false),
//        Task("Task 4", categories[0], "High", "Easy", false)
//    )}
    var tasks = testUser.currentTasks

    // State to track if the category dropdown menu is open
    var expanded by remember { mutableStateOf(false) }

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
                ElevatedButton(onClick = { expanded = true }) {
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
                onClick = { /* Handle button click */ }
            ) {
                Text("Priority")
            }
            // Difficulty Button - Needs to be implemented to sort Difficulty ratings
            ElevatedButton(onClick = { /* Handle button click */ }) {
                Text("Difficulty")
            }
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
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
                                .background(Color.LightGray.copy(alpha = 0.4f), shape = RoundedCornerShape(4.dp))
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
                                .border(1.dp, Color.Gray.copy(alpha = 0.4f), shape = RoundedCornerShape(4.dp))
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
                                // Priority icons - Will find another approach to show priority that clutters screen less
//                                repeat(getPriorityIconCount(task.priority)) {
//                                    Icon(
//                                        imageVector = Icons.Outlined.PriorityHigh,
//                                        contentDescription = "Priority High",
//                                        modifier = Modifier
//                                            .padding(start = 0.dp, end = 0.dp)
//                                    )
//                                }
                            }
                        }
                    }
                }
            }
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

// Get the corresponding color for a task difficulty level
@Composable
fun getDifficultyColor(difficulty: String): Color {
    return when (difficulty) {
        "Easy" -> Color.Green
        "Medium" -> Color.Blue
        "Hard" -> Color.Red
        else -> Color.Gray
    }
}

// Get the number of priority icons to display based on the task priority
//fun getPriorityIconCount(priority: String): Int {
//    return when (priority) {
//        "High" -> 2
//        "Medium" -> 1
//        else -> 0
//    }
//}






