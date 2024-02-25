package com.cs4520.assignment3.models

class CalculatorModel {
    fun add(
        a: Double,
        b: Double,
    ): Double {
        return a + b
    }

    fun subtract(
        a: Double,
        b: Double,
    ): Double {
        return a - b
    }

    fun multiply(
        a: Double,
        b: Double,
    ): Double {
        return a * b
    }

    fun divide(
        a: Double,
        b: Double,
    ): Double {
        if (b == 0.0) {
            // a / b will not throw an exception if b is 0, but it will return Infinity
            // to avoid this, we throw an exception if b is 0
            throw IllegalArgumentException("Cannot divide by zero.")
        }

        return a / b
    }
}
