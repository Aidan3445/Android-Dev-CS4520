package com.cs4520.assignment3.mvp

interface MVPCalculatorContract {
    interface View {
        fun setResult(result: String)

        fun setError(error: String)

        fun clearFields()
    }

    interface Presenter {
        fun onAddButtonClicked(
            firstNumber: String,
            secondNumber: String,
        )

        fun onSubtractButtonClicked(
            firstNumber: String,
            secondNumber: String,
        )

        fun onMultiplyButtonClicked(
            firstNumber: String,
            secondNumber: String,
        )

        fun onDivideButtonClicked(
            firstNumber: String,
            secondNumber: String,
        )
    }
}
