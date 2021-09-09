package com.example.shoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.shoppal.R
import com.example.shoppal.firebase.Firebase

class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        setActionBarWithBack()
        //Sends password reset email to the given email address
        findViewById<View>(R.id.btn_submit).setOnClickListener {
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

}