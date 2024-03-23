package com.cs4520.assignment5.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cs4520.assignment5.R
import com.cs4520.assignment5.databinding.LoginFragmentBinding
import com.cs4520.assignment5.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar

// the login fragment
class LoginFragment : Fragment(R.layout.login_fragment) {
    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
        viewModel = LoginViewModel()

        // observe live data
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            // show error message
            val snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
            snackBar.setAnchorView(binding.username)
            snackBar.show()
        }
        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            // navigate to the home fragment
            Navigation.findNavController(binding.root).navigate(R.id.login_action)
            // clear the username and password fields
            binding.username.text.clear()
            binding.password.text.clear()
        }

        // set onclick listener for the login button
        binding.loginButton.setOnClickListener {
            viewModel.tryLogin(binding.username.text.toString(), binding.password.text.toString())
        }
    }
}
