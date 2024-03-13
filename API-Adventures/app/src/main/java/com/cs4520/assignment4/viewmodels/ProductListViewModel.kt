package com.cs4520.assignment4.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment4.models.Product
import com.cs4520.assignment4.models.ProductRepository
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {
    // fetch handlers
    private val productsData = MutableLiveData<Set<Product>>()
    val products get() = productsData
    private val isFetchingData = MutableLiveData<Boolean>()
    val isFetching get() = isFetchingData

    // pagination handlers
    private val pageNumberData = MutableLiveData(1)
    val pageNumber get() = pageNumberData
    private val hasPrevData = MutableLiveData(false)
    val hasPrev get() = hasPrevData
    private val hasNextData = MutableLiveData(true)
    val hasNext get() = hasNextData

    // error handlers
    private val errorMessageData = MutableLiveData<String>()
    val errorMessage get() = errorMessageData

    // model fetch call
    fun fetchProducts() {
        isFetchingData.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.fetch(pageNumberData.value ?: 1)

                // handle empty data message
                if (response.isEmpty()) {
                    throw ProductRepository.FetchException("No products available")
                }

                productsData.postValue(response)
            } catch (e: ProductRepository.FetchException) {
                errorMessageData.postValue(e.message)
            } finally {
                isFetchingData.postValue(false)
            }
        }
    }

    // page navigation
    fun prevPage() {
        val nextPage = (pageNumberData.value ?: 1) - 1
        pageNumberData.value = nextPage
        if (nextPage <= 1) hasPrevData.postValue(false)
        if (nextPage == 3) hasNextData.postValue(true)

        // make call
        fetchProducts()
    }

    fun nextPage() {
        val nextPage = (pageNumberData.value ?: 1) + 1
        pageNumberData.value = nextPage
        if (nextPage >= 4) hasNextData.postValue(false)
        if (nextPage == 2) hasPrevData.postValue(true)

        // make call
        fetchProducts()
    }
}
