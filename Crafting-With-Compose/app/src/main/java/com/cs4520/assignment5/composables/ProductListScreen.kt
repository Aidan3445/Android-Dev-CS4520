package com.cs4520.assignment5.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cs4520.assignment5.composables.components.PaginationControls
import com.cs4520.assignment5.composables.components.ProductList
import com.cs4520.assignment5.composables.components.ReloadControls
import com.cs4520.assignment5.viewmodels.ProductListViewModel

@Composable
fun ProductListScreen(viewModel: ProductListViewModel) {
    // observe data from the view model
    val products by viewModel.products.observeAsState()
    val isFetching by viewModel.isFetching.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()
    val pageNumber by viewModel.pageNumber.observeAsState()
    val hasPrev by viewModel.hasPrev.observeAsState()
    val jumpToPage1 by viewModel.jumpToPage1.observeAsState()

    // effect only once
    LaunchedEffect(Unit) {
        // make initial fetch
        viewModel.fetchProducts()
        // trigger product refresh worker
        viewModel.triggerProductRefresh()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Box to center content above the pagination controls
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            if (isFetching == true) {
                // show loading spinner
                CircularProgressIndicator()
            } else if (errorMessage.isNullOrEmpty()) {
                // show product list
                ProductList(products ?: emptySet())
            } else {
                // show error message and reload button
                ReloadControls(errorMessage!!) { viewModel.fetchProducts() }
            }
        }

        PaginationControls(
            pageNumber = pageNumber ?: 1,
            hasPrev = hasPrev ?: false,
            jumpToPage1 = jumpToPage1 ?: false,
            prev = { viewModel.prevPage() },
            next = { viewModel.nextPage() },
            toPage1 = { viewModel.jumpToPage1() },
        )
    }
}
