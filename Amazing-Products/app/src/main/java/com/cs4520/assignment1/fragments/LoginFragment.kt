package com.cs4520.assignment1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cs4520.assignment1.R
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM1 = "username"
private const val ARG_PARAM2 = "password"

// the login fragment
class LoginFragment : Fragment(R.layout.login_fragment) {
    private var username: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)
            password = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: View = inflater.inflate(R.layout.login_fragment, container, false)

        // set onclick listener for the login button
        view.findViewById<View>(R.id.login_button)?.setOnClickListener {
            login(view)
        }

        return view
    }

    // login function
    private fun login(view: View) {
        // get username and password
        val username = view.findViewById<View>(R.id.username)?.let { it as EditText }
        val password = view.findViewById<View>(R.id.password)?.let { it as EditText }

        // check if username and password are not null and if they are correct
        if (username != null && password != null &&
            username.text.toString() == "admin" && password.text.toString() == "admin"
        ) {
            // clear the username and password fields
            username.text.clear()
            password.text.clear()

            Navigation.findNavController(view).navigate(R.id.login_action)
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
