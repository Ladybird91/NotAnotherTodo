package com.hw.notanothertodo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

fun navigationBarMenu() : List<Pair<ImageVector, String>>{
    return listOf(Icons.Outlined.CheckBox to "Tasks", Icons.Outlined.Redeem to "Prizes", Icons.Outlined.AccountBox to "User")
}

@Composable
fun CustomNavigationBar(callback : (String)->Unit){

    var selectedIndex by remember{ mutableStateOf(0) }
    val items = navigationBarMenu()

    NavigationBar() {
        items.forEachIndexed { index, data ->
            NavigationBarItem(
                selected = selectedIndex==index,
                onClick = {
                    selectedIndex=index
                    callback(data.second)
                },
                label = {
                    Text(text = data.second)
                },
                icon = {
                    Icon(imageVector = data.first, contentDescription = "navigation bar icons")
                })
        }
    }
}