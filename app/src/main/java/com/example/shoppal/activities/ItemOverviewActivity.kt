package com.example.shoppal.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.shoppal.R
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.models.Product
import com.example.shoppal.room.daos.CartDao
import com.example.shoppal.room.daos.ProductDao
import com.example.shoppal.room.databases.RoomDatabase
import com.example.shoppal.room.entities.CartOrder
import com.example.shoppal.room.entities.CurrentProduct
import com.example.shoppal.utils.Constants
import java.util.*

class ItemOverviewActivity : AppCompatActivity(), View.OnClickListener {
    private var currentUserId = ""
    private lateinit var cartDao: CartDao
    private lateinit var product: Product
    private var addToCartIsClicked = false
    private lateinit var cartTextView: TextView
    private lateinit var productDao: ProductDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_overview)

        currentUserId = Firebase(this).currentUserId()
        cartDao = Room.databaseBuilder(
            this@ItemOverviewActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().cartDao()

        productDao = Room.databaseBuilder(
            this@ItemOverviewActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().productDao()

        val currentUserId = Firebase(this@ItemOverviewActivity).currentUserId()
        cartTextView = findViewById(R.id.text_cart)
        val bundle = intent.getBundleExtra(Constants.SHOPPING_ITEM_BUNDLE)
        product = bundle!!.getParcelable(Constants.ITEM_DATA)!!
        Glide.with(this).load(product.img).into(findViewById(R.id.shopping_item_imageview))
        findViewById<TextView>(R.id.text_book_name).text = product.name
        findViewById<TextView>(R.id.text_book_author).text = "By ${product.author}"
        findViewById<TextView>(R.id.text_price).text = product.price.toString()
        findViewById<TextView>(R.id.text_image_description).text = product.description

        if (cartDao.exists(product.id, currentUserId)) {
            addToCartIsClicked = true
            cartTextView.text = "GO TO CART"
        }
        findViewById<View>(R.id.btn_add_to_cart).setOnClickListener(this)
        findViewById<View>(R.id.btn_buy).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_add_to_cart -> {
                    if (!addToCartIsClicked) {
                        saveOrderToCart()
                        cartTextView.text = getString(R.string.go_to_cart_string)
                        addToCartIsClicked = true
                    } else {
                        startActivity(Intent(this@ItemOverviewActivity, CartActivity::class.java))
                        finish()
                    }
                }
                R.id.btn_buy -> {
                    productDao.deleteProducts(currentUserId)
                    productDao.insertProduct(
                        CurrentProduct(
                            product.id,
                            currentUserId,
                            product.name,
                            product.price,
                            product.img,
                            1
                        )
                    )
                    val intent =
                        Intent(this@ItemOverviewActivity, SelectAddressActivity::class.java)
                    intent.putExtra(Constants.DIRECT_BUY_STATUS, true)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun saveOrderToCart() {
        cartDao.insertOrder(
            CartOrder(
                product.id,
                currentUserId,
                product.name,
                product.price,
                product.img,
                1
            )
        )
    }
}