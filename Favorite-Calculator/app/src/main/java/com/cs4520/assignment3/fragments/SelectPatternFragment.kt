package com.cs4520.assignment3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cs4520.assignment3.R
import com.cs4520.assignment3.databinding.SelectPatternBinding

class SelectPatternFragment : Fragment(R.layout.select_pattern) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = SelectPatternBinding.inflate(inflater, container, false)

        binding.MVPButton.setOnClickListener {
            // navigate to the MVP fragment
            Navigation.findNavController(binding.root).navigate(R.id.mvp_action)
        }

        binding.MVVMButton.setOnClickListener {
            // navigate to the MVVM fragment
            Navigation.findNavController(binding.root).navigate(R.id.mvvm_action)
        }

        return binding.root
    }
}
