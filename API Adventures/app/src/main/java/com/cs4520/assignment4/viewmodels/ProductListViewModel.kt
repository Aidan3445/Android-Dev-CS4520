package com.cs4520.assignment4.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment4.models.Product
import com.cs4520.assignment4.models.ProductRepository
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {
    // product handlers
    private val productsData = MutableLiveData<Set<Product>>()
    val products get() = productsData

    // error handlers
    private val errorMessageData = MutableLiveData<String>()
    val errorMessage get() = errorMessageData

    // model fetch call
    fun fetchProducts(pageNumber: Int) {
        viewModelScope.launch {
            try {
                val response = repository.fetch(pageNumber)
                productsData.postValue(response)
            } catch (e: ProductRepository.FetchException) {
                errorMessageData.postValue(e.message)
            }
        }
    }
}
