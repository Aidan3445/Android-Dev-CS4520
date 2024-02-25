package com.cs4520.assignment1.data

sealed class Product() {
    data class Equipment(val name: String, val price: Int) : Product()

    data class Food(val name: String, val expirationDate: String, val price: Int) : Product()
}
