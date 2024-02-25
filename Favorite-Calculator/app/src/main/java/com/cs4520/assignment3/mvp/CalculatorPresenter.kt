package com.cs4520.assignment3.mvp

import com.cs4520.assignment3.models.CalculatorModel
import java.lang.IllegalArgumentException
import java.text.DecimalFormat

class CalculatorPresenter(private val view: MVPCalculatorContract.View) :
    MVPCalculatorContract.Presenter {
    private val model: CalculatorModel = CalculatorModel()

    override fun onAddButtonClicked(
        firstNumber: String,
        secondNumber: String,
    ) {
        try {
            val num1 = firstNumber.toDouble()
            val num2 = secondNumber.toDouble()

            view.setResult(formatResult(model.add(num1, num2)))
            view.clearFields()
        } catch (e: NumberFormatException) {
            view.setError("Invalid or blank numbers.")
            return
        } catch (e: IllegalArgumentException) {
            view.setError(e.message ?: "An error occurred.")
        } catch (e: Exception) {
            view.setError("An error occurred: ${e.message}")
        }
    }

    override fun onSubtractButtonClicked(
        firstNumber: String,
        secondNumber: String,
    ) {
        try {
            val num1 = firstNumber.toDouble()
            val num2 = secondNumber.toDouble()

            view.setResult(formatResult(model.subtract(num1, num2)))
            view.clearFields()
        } catch (e: NumberFormatException) {
            view.setError("Invalid or blank numbers.")
            return
        } catch (e: IllegalArgumentException) {
            view.setError(e.message ?: "An error occurred.")
        } catch (e: Exception) {
            view.setError("An error occurred: ${e.message}")
        }
    }

    override fun onMultiplyButtonClicked(
        firstNumber: String,
        secondNumber: String,
    ) {
        try {
            val num1 = firstNumber.toDouble()
            val num2 = secondNumber.toDouble()

            view.setResult(formatResult(model.multiply(num1, num2)))
            view.clearFields()
        } catch (e: NumberFormatException) {
            view.setError("Invalid or blank numbers.")
            return
        } catch (e: IllegalArgumentException) {
            view.setError(e.message ?: "An error occurred.")
        } catch (e: Exception) {
            view.setError("An error occurred: ${e.message}")
        }
    }

    override fun onDivideButtonClicked(
        firstNumber: String,
        secondNumber: String,
    ) {
        try {
            val num1 = firstNumber.toDouble()
            val num2 = secondNumber.toDouble()

            view.setResult(formatResult(model.divide(num1, num2)))
            view.clearFields()
        } catch (e: NumberFormatException) {
            view.setError("Invalid or blank numbers.")
            return
        } catch (e: IllegalArgumentException) {
            view.setError(e.message ?: "An error occurred.")
        } catch (e: Exception) {
            view.setError("An error occurred: ${e.message}")
        }
    }

    private fun formatResult(result: Double): String {
        return DecimalFormat("#.####").format(result)
    }
}
