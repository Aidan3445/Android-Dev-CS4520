package com.cs4520.assignment5.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.cs4520.assignment5.composables.components.PaginationControls
import com.cs4520.assignment5.composables.components.ProductList
import com.cs4520.assignment5.database.ProductDatabase
import com.cs4520.assignment5.models.ProductRepository
import com.cs4520.assignment5.viewmodels.ProductListViewModel

@Composable
fun ProductListScreen(viewModel: ProductListViewModel) {
    // observe data from the view model
    val products by viewModel.products.observeAsState()
    val isFetching by viewModel.isFetching.observeAsState()
    // val message by viewModel.errorMessage.observeAsState()
    val pageNumber by viewModel.pageNumber.observeAsState()
    val hasPrev by viewModel.hasPrev.observeAsState()
    val jumpToPage1 by viewModel.jumpToPage1.observeAsState()

    // make initial fetch
    LaunchedEffect(true) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // if fetching, show loading bar
        if (isFetching == true) {
            Spacer(modifier = Modifier.weight(1f))
            CircularProgressIndicator()
        } else {
            ProductList(products ?: emptySet())
        }

        Spacer(modifier = Modifier.weight(1f))

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

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    ProductDatabase.init(LocalContext.current)
    val viewModel =
        ProductListViewModel(
            ProductRepository(
                ProductDatabase.getInstance().productDao(),
            ),
        )
    ProductListScreen(viewModel)
}
