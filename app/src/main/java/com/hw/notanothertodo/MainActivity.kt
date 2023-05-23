package com.hw.notanothertodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hw.notanothertodo.ui.theme.NotAnotherTodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotAnotherTodoTheme {
                CustomLayoutStructure()
            }
        }
    }
}

