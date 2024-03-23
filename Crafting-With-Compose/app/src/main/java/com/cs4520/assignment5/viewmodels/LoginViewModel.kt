package com.cs4520.assignment5.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs4520.assignment5.models.LoginModel

class LoginViewModel : ViewModel() {
    // establish model
    private val model: LoginModel = LoginModel()

    // login event handler
    private val loginSuccessEvent = MutableLiveData<Unit>()
    val loginSuccess get() = loginSuccessEvent

    // error handler
    private val errorMessageData = MutableLiveData<String>()
    val errorMessage get() = errorMessageData

    // login function
    fun tryLogin(
        username: String,
        password: String,
    ) {
        if (model.validateCredentials(username, password)) {
            loginSuccessEvent.postValue(Unit)
            errorMessageData.postValue("")
        } else {
            errorMessageData.postValue("Invalid username or password\nPlease Try Again")
        }
    }
}
