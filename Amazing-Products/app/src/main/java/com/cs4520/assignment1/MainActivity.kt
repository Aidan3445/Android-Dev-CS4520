package com.cs4520.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Amazing Products"

        val loginFragment: LoginFragment = LoginFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, loginFragment)
            commit()
        }
    }
}
