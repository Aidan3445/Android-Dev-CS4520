package com.cs4520.assignment5.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs4520.assignment5.viewmodels.LoginViewModel

@Preview(showBackground = true)
@Composable
fun LoginScreen(viewModel: LoginViewModel = LoginViewModel()) {
    // get error and success updates from the view model
    val errorMessage by viewModel.errorMessage.observeAsState()
    val loginSuccess by viewModel.loginSuccess.observeAsState()

    // use column to stack the username, password, and login button
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // remember fields
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // username field
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
        )

        // space between fields
        Spacer(modifier = Modifier.height(10.dp))

        // password field
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
        )

        Text(
            text = if (errorMessage == null) "" else errorMessage!!,
            modifier = Modifier.requiredHeight(40.dp),
            color = Color.Red,
            textAlign = TextAlign.Center,
        )

        // space between hidden error message and button
        Spacer(modifier = Modifier.height(350.dp))

        // login button
        Button(
            onClick = {
                viewModel.tryLogin(username, password)
                Log.d("LoginScreen", "Attempting to login")
            },
            modifier = Modifier.fillMaxWidth(.9f),
        ) {
            Text(text = "Login")
        }

        // handle login attempts
        LaunchedEffect(loginSuccess) {
            loginSuccess.let {
                // navigate to the home fragment
                Log.d("LoginScreen", "Navigating to home fragment")
                // clear the username and password fields
                username = ""
                password = ""
            }
        }
    }
}
