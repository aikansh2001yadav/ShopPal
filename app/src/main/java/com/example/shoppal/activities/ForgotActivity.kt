package com.example.shoppal.activities

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppal.R
import com.example.shoppal.firebase.Firebase

class ForgotActivity : AppCompatActivity() {

    /**
     * Stores reference of progress bar that shows progress
     */
    private var forgotProgress: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        forgotProgress = findViewById(R.id.activity_forgot_progress)
        setActionBarWithBack()
        //Sends password reset email to the given email address
        findViewById<View>(R.id.btn_submit).setOnClickListener {
            hideKeyboard()
            //Shows progress bar
            forgotProgress!!.visibility = View.VISIBLE
            val email = findViewById<EditText>(R.id.edit_text_email_forgot).text.toString()
            Firebase(this@ForgotActivity).sendPasswordResetEmail(email)
        }
    }

    /**
     * Sets back title icon for back pressing on the action bar
     */
    private fun setActionBarWithBack() {
        setSupportActionBar(findViewById(R.id.toolbar_forgot_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_title)
        }
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_forgot_activity).setNavigationOnClickListener { onBackPressed() }
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
        forgotProgress!!.visibility = View.GONE
    }
}