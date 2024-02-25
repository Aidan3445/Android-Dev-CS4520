package com.cs4520.assignment3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cs4520.assignment3.R
import com.cs4520.assignment3.databinding.CalculatorBinding
import com.cs4520.assignment3.mvp.CalculatorPresenter
import com.cs4520.assignment3.mvp.MVPCalculatorContract
import com.google.android.material.snackbar.Snackbar

class MVPFragment : Fragment(R.layout.calculator), MVPCalculatorContract.View {
    private lateinit var presenter: CalculatorPresenter
    private lateinit var binding: CalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // set binding and presenter
        binding = CalculatorBinding.inflate(inflater, container, false)
        presenter = CalculatorPresenter(this)

        // set the background color for mvp
        binding.root.setBackgroundColor(resources.getColor(R.color.mvp_bg, null))

        // set onclick listeners for the buttons
        binding.addButton.setOnClickListener {
            presenter.onAddButtonClicked(
                binding.firstNumber.text.toString(),
                binding.secondNumber.text.toString(),
            )
        }
        binding.subtractButton.setOnClickListener {
            presenter.onSubtractButtonClicked(
                binding.firstNumber.text.toString(),
                binding.secondNumber.text.toString(),
            )
        }
        binding.multiplyButton.setOnClickListener {
            presenter.onMultiplyButtonClicked(
                binding.firstNumber.text.toString(),
                binding.secondNumber.text.toString(),
            )
        }
        binding.divideButton.setOnClickListener {
            presenter.onDivideButtonClicked(
                binding.firstNumber.text.toString(),
                binding.secondNumber.text.toString(),
            )
        }

        return binding.root
    }

    override fun setResult(result: String) {
        // set the result
        binding.result.text = String.format("Result: %s", result)
    }

    override fun setError(error: String) {
        // set the error
        val snack = Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG)
        snack.setAnchorView(binding.firstNumber).show()
    }

    override fun clearFields() {
        // clear the text fields
        binding.firstNumber.text.clear()
        binding.secondNumber.text.clear()
    }
}
