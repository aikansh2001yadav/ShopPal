package com.example.shoppal.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shoppal.R
import com.example.shoppal.models.Product
import com.example.shoppal.utils.Constants

class ItemOverviewActivity : AppCompatActivity(), View.OnClickListener {
    private var addToCartIsClicked = false
    private lateinit var cartTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_overview)

        cartTextView = findViewById(R.id.text_cart)
        val bundle = intent.getBundleExtra(Constants.SHOPPING_ITEM_BUNDLE)
        val product = bundle!!.getParcelable<Product>(Constants.ITEM_DATA)
        if (product != null) {
            Glide.with(this).load(product.img).into(findViewById(R.id.shopping_item_imageview))
            findViewById<TextView>(R.id.text_book_name).text = product.name
            findViewById<TextView>(R.id.text_book_author).text = "By ${product.author}"
            findViewById<TextView>(R.id.text_price).text = product.price.toString()
            findViewById<TextView>(R.id.text_image_description).text = product.description
        }

        findViewById<View>(R.id.btn_add_to_cart).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_add_to_cart -> {
                    if (!addToCartIsClicked) {
                        saveOrderToCart()
                        cartTextView.text = "GO TO CART"
                        addToCartIsClicked = true
                    } else {
                        startActivity(Intent(this@ItemOverviewActivity, CartActivity::class.java))
                    }
                }
            }
        }
    }

    private fun saveOrderToCart() {
        //TODO save to cart
    }
}