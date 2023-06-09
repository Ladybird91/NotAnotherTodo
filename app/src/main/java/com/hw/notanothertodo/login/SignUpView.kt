package com.hw.notanothertodo.login

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import com.hw.notanothertodo.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.hw.notanothertodo.RESET
import com.hw.notanothertodo.ui.theme.md_theme_light_secondary
import dagger.hilt.android.lifecycle.HiltViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpView (
    openScreen: (String) -> Unit,
    viewModel: SignUpModel = hiltViewModel(),
    onNewValue: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit

    ) {
    val uiState by viewModel.uiState

    Scaffold() {
        Column(

            Modifier
                .fillMaxSize()
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
                            value = uiState.email,
                            onValueChange = { onNewValue(it) },
                            placeholder = { Text(text = "enter email address") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        )

                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = { onNewValue(it) },
                            placeholder = { Text(text = "enter password") },
                            singleLine = true,
                        )
                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = { onNewValue(it) },
                            placeholder = { Text(text = "re-enter password") },
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        Button(onClick = { viewModel.onSignupEnter(openAndPopUp)},
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(60.dp)) {
                            Text(text = "Create Account", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }


}