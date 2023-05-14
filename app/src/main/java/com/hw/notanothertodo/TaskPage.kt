package com.hw.notanothertodo

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

data class Category(
    val name: String,
    val priority: String
)

@Composable
fun TaskPageLayout(
    contentPadding: PaddingValues = PaddingValues()
) {
    val categories = listOf(
        Category("Home","High"),
        Category("Work","High"),
        Category("Chores","Low"),
    )


    var expanded by remember { mutableStateOf(false) }
    val categoryCheckedState = remember { mutableStateMapOf<Category, Boolean>() }
    categories.forEach { category ->
        categoryCheckedState[category] = true
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
            Box() {
                ElevatedButton(onClick = { expanded = true }) {
                    Text("Category")
                }
                CustomDropdownMenu(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    categories = categories,
                    categoryCheckedState = categoryCheckedState,
                    onCategoryClick = { /* Handle item selection */ }
                )
            }
            ElevatedButton(
                onClick = { /* Handle button click */ }
            ) {
                Text("Priority")
            }
            ElevatedButton(onClick = { /* Handle button click */ }) {
                Text("Difficulty")
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 16.dp)
        ) {
            items(categories) { category ->
                if (categoryCheckedState[category] == true) {
                    Text(text = category.name)
                }
            }
        }
    }
}

@Composable
fun CustomDropdownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    categories: List<Category>,
    categoryCheckedState: MutableMap<Category, Boolean>,
    onCategoryClick: (Category) -> Unit
) {

    var selectedCategories by remember { mutableStateOf(emptyList<Category>()) }

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
                            .semantics { contentDescription = "Switch to choose which categories to view" },
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
                ) {
                    categoryCheckedState[category] = !categoryCheckedState[category]!!
                    selectedCategories = if (category in selectedCategories) {
                        selectedCategories - category
                    } else {
                        selectedCategories + category
                    }
                }
            )
        }
    }
}



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

