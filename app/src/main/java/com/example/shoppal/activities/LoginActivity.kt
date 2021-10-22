package com.example.shoppal.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppal.R
import com.example.shoppal.firebase.Firebase


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    /**
     * Shows reference of progress bar that shows progress
     */
    private var loginProgress: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginProgress = findViewById(R.id.activity_login_progress)
        //Adding on click listener on below views
        findViewById<View>(R.id.btn_login).setOnClickListener(this)
        findViewById<TextView>(R.id.text_register).setOnClickListener(this)
        findViewById<TextView>(R.id.text_forgot).setOnClickListener(this)
    }

    /**
     * Tries to login and get user profile details from firestore after successful login
     */
    private fun tryLogin() {
        hideKeyboard()
        //Shows progress bar
        loginProgress!!.visibility = View.VISIBLE
        val email = findViewById<EditText>(R.id.edit_text_email).text.toString()
        val password = findViewById<EditText>(R.id.edit_text_password).text.toString()
        //If all the input information given by the user is valid,then login user
        if (validateEmailPassword(email, password)) {
            Firebase(this@LoginActivity).loginUser(email, password)
        } else {
            hideProgressBar()
            Toast.makeText(this, "Email and password not correct. Try again", Toast.LENGTH_LONG)
                .show()
        }
    }

    /**
     * Validates the input information given by the user and return true if all information is valid and invalid otherwise
     */
    private fun validateEmailPassword(email: String, password: String): Boolean {
        // Check for a valid email address.
        val isEmailValid: Boolean = if (email.isEmpty()) {
            false
        } else Patterns.EMAIL_ADDRESS.matcher(email).matches()

        // Check for a valid password.
        val isPasswordValid: Boolean = if (password.isEmpty()) {
            false
        } else password.length >= 6

        return isEmailValid and isPasswordValid
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_login -> {
                    tryLogin()
                }
                R.id.text_register -> {
                    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                }
                R.id.text_forgot -> {
                    startActivity(Intent(this@LoginActivity, ForgotActivity::class.java))
                }
            }
        }
    }

    /**
     * Hides keyboard
     */
    private fun hideKeyboard() {
        try {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Hides progress bar
     */
    fun hideProgressBar() {
        loginProgress!!.visibility = View.GONE
    }
}