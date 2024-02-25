package com.cs4520.assignment3.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs4520.assignment3.models.CalculatorModel
import java.lang.IllegalArgumentException
import java.text.DecimalFormat

class CalculatorViewModel : ViewModel() {
    private val model: CalculatorModel = CalculatorModel()

    private val resultData = MutableLiveData<String>()
    val resultLiveData: MutableLiveData<String> = resultData

    private val errorData = MutableLiveData<String>()
    val errorLiveData: MutableLiveData<String> = errorData

    fun performCalculation(
        firstNumber: String,
        secondNumber: String,
        operation: String,
    ) {
        try {
            val num1 = firstNumber.toDouble()
            val num2 = secondNumber.toDouble()

            val result =
                when (operation) {
                    "add" -> model.add(num1, num2)
                    "subtract" -> model.subtract(num1, num2)
                    "multiply" -> model.multiply(num1, num2)
                    "divide" -> model.divide(num1, num2)
                    else -> {
                        errorData.value = "Invalid operation: '$operation'"
                        return
                    }
                }

            resultData.value = DecimalFormat("#.####").format(result)
        } catch (e: NumberFormatException) {
            // if either number is invalid, set error message and return
            errorData.value = "Invalid or blank numbers."
            return
        } catch (e: IllegalArgumentException) {
            errorData.value = e.message
        } catch (e: Exception) {
            errorData.value = "An error occurred: ${e.message}"
        }
    }
}
