package com.cs4520.assignment5.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ReloadControls(
    message: String = "Random Error Occurred",
    reload: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(100.dp, 0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // display error message
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
        )
        // reload button
        Button(
            onClick = { reload() },
        ) {
            Text(text = "Reload")
        }
    }
}
