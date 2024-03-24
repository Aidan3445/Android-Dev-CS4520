package com.cs4520.assignment5.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment5.models.Product
import com.cs4520.assignment5.models.ProductRepository
import com.cs4520.assignment5.workManager.ProductWorkScheduler
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val repository: ProductRepository,
    application: Application,
) : AndroidViewModel(application) {
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
    private val jumpToP1 = MutableLiveData(false)
    val jumpToPage1 get() = jumpToP1

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
                errorMessageData.postValue("")
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
        if (nextPage < 10) jumpToP1.postValue(false)

        // make call
        fetchProducts()
    }

    fun nextPage() {
        val nextPage = (pageNumberData.value ?: 1) + 1
        pageNumberData.value = nextPage
        if (nextPage == 2) hasPrevData.postValue(true)
        if (nextPage >= 10) jumpToP1.postValue(true)

        // make call
        fetchProducts()
    }

    fun jumpToPage1() {
        pageNumberData.value = 1
        hasPrevData.postValue(false)
        jumpToP1.postValue(false)

        // make call
        fetchProducts()
    }

    fun triggerProductRefresh() {
        // schedule work
        ProductWorkScheduler().scheduleProductWork(
            getApplication<Application>().applicationContext,
        )
    }
}
