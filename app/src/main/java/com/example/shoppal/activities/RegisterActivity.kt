package com.example.shoppal.activities

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppal.R
import com.example.shoppal.firebase.Firebase

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<TextView>(R.id.text_login_back).setOnClickListener(this)
        findViewById<View>(R.id.btn_register).setOnClickListener(this)
        setActionBarWithBack()
    }

    private fun setActionBarWithBack() {
        setSupportActionBar(findViewById(R.id.toolbar_register_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_title)
        }
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_register_activity).setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateDetails(): Boolean {
        val email = findViewById<EditText>(R.id.edit_text_register_email).text.toString()
        val password = findViewById<EditText>(R.id.edit_text_register_password).text.toString()
        val confirmPassword =
            findViewById<EditText>(R.id.edit_text_confirm_password).text.toString()

        if (findViewById<EditText>(R.id.edit_text_register_name).text.toString().isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Please enter name", Toast.LENGTH_SHORT).show()
            return false
        } else if (findViewById<EditText>(R.id.edit_text_register_last_name).text.toString()
                .isEmpty()
        ) {
            Toast.makeText(this@RegisterActivity, "Please enter last name", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (email.isEmpty() or !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(
                this@RegisterActivity,
                "Please enter email correctly",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (password.isEmpty() or (password.length < 6)) {
            Toast.makeText(
                this@RegisterActivity,
                "Please enter password correctly",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (confirmPassword != password) {
            Toast.makeText(
                this@RegisterActivity,
                "Entered password is not same as you typed earlier",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (!findViewById<CheckBox>(R.id.checkbox_terms).isChecked) {
            Toast.makeText(
                this@RegisterActivity,
                "Please agree to all terms and conditions",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {
            return true
        }
    }

    private fun tryRegister() {
        if (validateDetails()) {
            val email = findViewById<EditText>(R.id.edit_text_register_email).text.toString()
            val password = findViewById<EditText>(R.id.edit_text_register_password).text.toString()
            Firebase(this@RegisterActivity).createUser(email, password)
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.text_login_back -> {
                    onBackPressed()
                }
                R.id.btn_register -> {
                    tryRegister()
                }
            }
        }
    }
}