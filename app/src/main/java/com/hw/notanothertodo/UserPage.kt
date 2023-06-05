package com.hw.notanothertodo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun UserScreen() {
    // Temporary hard coded values - use until we figure out passing User info effectively
    val lifetimePoints = 1574
    val taskCompleted = 34
    val pendingTasks = 14

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, top = 110.dp, bottom = 10.dp, end = 15.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .border(
                        3.dp,
                        Color(0xFF897A9E).copy(alpha = 0.3f),
                        shape = CircleShape
                    )
                    .size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = lifetimePoints.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Lifetime Points",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }

            Spacer(modifier = Modifier.height(25.dp))

            Row {
                Box(
                    modifier = Modifier
                        .border(
                            3.dp,
                            Color(0xFF897A9E).copy(alpha = 0.5f),
                            shape = CutCornerShape(10.dp)
                        )
                        .weight(1f)
                        .size(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            text = taskCompleted.toString(),
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Tasks Competed",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }

                Spacer(modifier = Modifier.width(25.dp))

                Box(
                    modifier = Modifier
                        .border(
                            3.dp,
                            Color(0xFF897A9E).copy(alpha = 0.5f),
                            shape = CutCornerShape(10.dp)
                        )
                        .size(100.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            text = pendingTasks.toString(),
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Pending Tasks",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }
            }
        }
    }
}
