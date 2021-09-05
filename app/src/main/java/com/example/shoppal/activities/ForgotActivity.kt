package com.example.shoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppal.R

class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        setActionBarWithBack()
    }

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