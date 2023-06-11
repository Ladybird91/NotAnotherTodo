package com.hw.notanothertodo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AboutScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, top = 110.dp, bottom = 10.dp, end = 15.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(13.dp)
            )

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Phasellus ut mi eget mi fringilla vulputate vitae in purus. " +
                        "Etiam a tempor lectus. In id dapibus tortor, vitae tincidunt " +
                        "risus. Quisque bibendum libero sed augue rutrum bibendum. " +
                        "Suspendisse potenti. Nam vel libero rhoncus, sodales mi " +
                        "nec, interdum erat.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 30.dp)
            )

            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.width(180.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.github_icon),
                            contentDescription = "github icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Elizabeth Hopper",
                            style =  MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 10.dp, bottom = 10.dp)
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.lizzie_profile_pic),
                        contentDescription = "Picture of Lizzie",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = "Suspendisse potenti. Nam vel libero rhoncus, sodales mi",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 10.dp, bottom = 30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(30.dp))

                Column(
                    modifier = Modifier.width(180.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.github_icon),
                            contentDescription = "Small Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Jason Beutler",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 10.dp, bottom = 10.dp)
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.jason_profile_pic),
                        contentDescription = "Picture of Jason",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = "Suspendisse potenti. Nam vel libero rhoncus, sodales mi",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 10.dp, bottom = 30.dp)
                    )
                }

            }
        }
    }
}



