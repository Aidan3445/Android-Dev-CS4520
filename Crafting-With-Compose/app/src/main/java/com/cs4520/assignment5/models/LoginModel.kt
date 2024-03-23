package com.cs4520.assignment5.models

class LoginModel {
    fun validateCredentials(
        username: String,
        password: String,
    ): Boolean {
        // only admin can login
        return username == "admin" && password == "admin"
    }
}
