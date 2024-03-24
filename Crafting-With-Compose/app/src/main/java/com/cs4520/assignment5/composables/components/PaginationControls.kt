package com.cs4520.assignment5.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PaginationControls(
    pageNumber: Number = 10,
    hasPrev: Boolean = true,
    jumpToPage1: Boolean = true,
    prev: () -> Unit = {},
    next: () -> Unit = {},
    toPage1: () -> Unit = {},
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .requiredHeight(40.dp)
                .padding(0.dp, 5.dp)
                .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        // jump to page one if available
        Button(
            onClick = {
                toPage1()
            },
            modifier =
                Modifier
                    .alpha(if (jumpToPage1) 1f else 0f)
                    .requiredWidth(65.dp)
                    .padding(5.dp, 0.dp),
            enabled = jumpToPage1,
        ) {
            Text(text = "1", style = MaterialTheme.typography.labelMedium)
        }

        // previous page button
        Button(
            onClick = {
                prev()
            },
            modifier = Modifier.requiredWidth(105.dp),
            enabled = hasPrev,
        ) {
            Text(text = "Previous", style = MaterialTheme.typography.labelMedium)
        }

        // page number
        Text(
            text = "$pageNumber",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(20.dp, 0.dp),
        )

        // next page button
        Button(
            onClick = {
                next()
            },
            modifier = Modifier.requiredWidth(105.dp),
        ) {
            Text(text = "Next", style = MaterialTheme.typography.labelMedium)
        }

        // spacer to fill space on the right
        Spacer(modifier = Modifier.width(70.dp))
    }
}
