package com.cs4520.assignment3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cs4520.assignment3.R
import com.cs4520.assignment3.databinding.CalculatorBinding
import com.cs4520.assignment3.mvvm.CalculatorViewModel
import com.google.android.material.snackbar.Snackbar

class MVVMFragment : Fragment(R.layout.calculator) {
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = CalculatorBinding.inflate(inflater, container, false)
        // initialize the view model
        viewModel = CalculatorViewModel()

        // set background color for mvvm
        binding.root.setBackgroundColor(resources.getColor(R.color.mvvm_bg, null))

        // set onclick listeners for the buttons
        binding.addButton.setOnClickListener {
            viewModel.performCalculation(
                binding.firstNumber.text.toString(),
                binding.secondNumber.text.toString(),
                "add",
            )
        }
        binding.subtractButton.setOnClickListener {
            viewModel.performCalculation(
                binding.firstNumber.text.toString(),
                binding.secondNumber.text.toString(),
                "subtract",
            )
        }
        binding.multiplyButton.setOnClickListener {
            viewModel.performCalculation(
                binding.firstNumber.text.toString(),
                binding.secondNumber.text.toString(),
                "multiply",
            )
        }
        binding.divideButton.setOnClickListener {
            viewModel.performCalculation(
                binding.firstNumber.text.toString(),
                binding.secondNumber.text.toString(),
                "divide",
            )
        }

        // observe the result
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            // set the result
            binding.result.text = String.format("Result: %s", it)
            // clear the text fields
            binding.firstNumber.text.clear()
            binding.secondNumber.text.clear()
        }

        // observe the errors
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            // set the error
            val snack = Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG)
            snack.setAnchorView(binding.firstNumber).show()
        }

        return binding.root
    }
}
