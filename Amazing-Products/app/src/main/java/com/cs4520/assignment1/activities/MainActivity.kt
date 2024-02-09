package com.cs4520.assignment1.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cs4520.assignment1.R
import com.cs4520.assignment1.fragments.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginFragment: LoginFragment = LoginFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, loginFragment)
            commit()
        }
    }
}
