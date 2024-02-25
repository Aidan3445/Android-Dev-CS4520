package com.cs4520.assignment1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cs4520.assignment1.R
import com.cs4520.assignment1.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar

// the login fragment
class LoginFragment : Fragment(R.layout.login_fragment) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = LoginFragmentBinding.inflate(inflater, container, false)

        // set onclick listener for the login button
        binding.loginButton.setOnClickListener {
            login(binding)
        }

        return binding.root
    }

    // login function
    private fun login(binding: LoginFragmentBinding) {
        // get username and password
        val username = binding.username
        val password = binding.password

        // check if username and password are not null and if they are correct
        if (username.text.toString() == "admin" && password.text.toString() == "admin"
        ) {
            // clear the username and password fields
            username.text.clear()
            password.text.clear()

            Navigation.findNavController(binding.root).navigate(R.id.login_action)
        } else {
            // notify user of invalid username or password
            val snackBar =
                Snackbar.make(
                    requireView(),
                    "Invalid username or password.\nPlease Try Again",
                    Snackbar.LENGTH_LONG,
                )
            snackBar.setAnchorView(username)
            snackBar.show()
        }
    }
}
