package com.hw.notanothertodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hw.notanothertodo.ui.theme.NotAnotherTodoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentPage by remember{ mutableStateOf(navigationBarMenu().firstOrNull()?.second) }
            NotAnotherTodoTheme {
                Scaffold(
                    bottomBar = {
                        CustomNavigationBar{
                            currentPage = it
                        }
                    }
            ) {Box(modifier= Modifier
                    .fillMaxSize()
                    .padding(it),
                    contentAlignment = Alignment.Center) {
                    Text(text = "$currentPage Page")
                }

                }
            }
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotAnotherTodoTheme {
        Greeting("Android")
    }
}