package com.cs4520.assignment1.data

data class Product(val name: String, val type: String, val date: String?, val price: String) {
    constructor(name: String, type: String, date: String?, price: Int) :
        this(name, type, if (!date) "" else date, "$$price.00")
}

private operator fun String?.not(): Boolean {
    return this.isNullOrEmpty() || this.lowercase() == "null"
}
