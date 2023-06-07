package com.hw.notanothertodo

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.hw.notanothertodo.ui.theme.md_theme_light_secondary
import dagger.hilt.android.lifecycle.HiltViewModel

private lateinit var auth: FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
fun LoginView(
    viewModel: HiltViewModel = HiltViewModel()

) {

    Modifier.background(md_theme_light_secondary)

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    Scaffold() {
        Column(

            Modifier.fillMaxSize()
                .background(md_theme_light_secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = "Not Another To Do",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.LightGray,
                modifier = Modifier
                    .padding()
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.passionflower_todo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .weight(4f)
                    .size(360.dp)
            )
            Card(
                Modifier
                    .weight(5f)
                    .padding(12.dp),
                shape = RoundedCornerShape(30.dp),
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(32.dp)

                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        OutlinedTextField(
                            value = emailValue.value,
                            onValueChange = { emailValue.value = it },
                            label = { Text(text = "E-mail") },
                            placeholder = { Text(text = "enter email here") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        )

                        OutlinedTextField(
                            value = passwordValue.value,
                            onValueChange = { passwordValue.value = it },
                            label = { Text("Password") },
                            placeholder = { Text(text = "enter password here") },
                            singleLine = true,
                            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        Button(onClick = {},
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(60.dp)) {
                            Text(text = "Reset Password", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }

                        Spacer(modifier = Modifier.padding(20.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),Arrangement.Center
                        ) {
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = "About",fontSize = 18.sp)
                            }

                        }

                        Row(modifier = Modifier.fillMaxWidth(),Arrangement.Center,verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "No account?",fontSize = 20.sp)
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = "Sign up!")
                            }
                        }

                    }
                }
            }
        }
    }
}