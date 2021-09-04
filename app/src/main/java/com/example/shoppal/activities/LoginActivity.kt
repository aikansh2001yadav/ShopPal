package com.example.shoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.shoppal.R

class LoginActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<View>(R.id.btn_login).setOnClickListener(this)
        findViewById<TextView>(R.id.text_register).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when(view.id){
                R.id.btn_login -> {
                    Toast.makeText(this@LoginActivity, "Hello", Toast.LENGTH_LONG).show()
                }
                R.id.text_register -> {
                    Toast.makeText(this@LoginActivity, "Register", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}