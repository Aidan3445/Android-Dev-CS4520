package com.cs4520.assignment5.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs4520.assignment5.navigation.NavGraph
import com.cs4520.assignment5.viewmodels.LoginViewModel

@Preview(showBackground = true)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = LoginViewModel(),
    navController: NavController = rememberNavController(),
) {
    // get error and success updates from the view model
    val errorMessage by viewModel.errorMessage.observeAsState()
    val loginSuccess by viewModel.loginSuccess.observeAsState()

    // error message notification
    val state = remember { SnackbarHostState() }
    SnackbarHost(
        hostState = remember { state },
        modifier = Modifier.fillMaxWidth(),
    )

    // use column to stack the username, password, and login button
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // remember fields
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column {
            // username field
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.padding(bottom = 10.dp),
            )

            // password field
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
            )
        }

        // login button
        Button(
            onClick = {
                viewModel.tryLogin(username, password)
            },
            modifier = Modifier.fillMaxWidth(.9f),
        ) {
            Text(text = "Login")
        }

        // handle login attempts
        LaunchedEffect(loginSuccess) {
            loginSuccess?.let {
                // navigate to the home fragment
                navController.navigate(NavGraph.productListScreen)
                // clear the username and password fields
                username = ""
                password = ""
            }
        }

        LaunchedEffect(errorMessage) {
            if (errorMessage.isNullOrEmpty()) return@LaunchedEffect

            state.showSnackbar(errorMessage!!)
            viewModel.clearErrorMessage()
        }
    }
}
