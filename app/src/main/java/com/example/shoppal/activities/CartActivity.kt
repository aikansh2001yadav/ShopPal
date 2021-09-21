package com.example.shoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppal.R
import com.example.shoppal.fragments.CartFragment


class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_cart_container, CartFragment()).commit()
    }
}