package com.example.shoppal.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
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
    /**
     * Stores reference of progress bar that shows progress
     */
    private var itemOverviewActivityProgress: ProgressBar? = null

    /**
     * Stores user id of the current user
     */
    private var currentUserId = ""

    /**
     * Stores the reference of cartDao that performs actions on cart order
     */
    private lateinit var cartDao: CartDao

    /**
     * Stores the current product
     */
    private lateinit var product: Product

    /**
     * Stores whether add to cart button is clicked
     */
    private var addToCartIsClicked = false

    /**
     * Stores the reference of cartTextView which will change accordingly
     */
    private lateinit var cartTextView: TextView

    /**
     * Stores the reference of productDao that performs actions on product to be bought directly
     */
    private lateinit var productDao: ProductDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_overview)

        itemOverviewActivityProgress = findViewById(R.id.activity_item_overview_progress)
        //Storing user id of the current user
        currentUserId = Firebase(this).currentUserId()
        //Initialising cartDao
        cartDao = Room.databaseBuilder(
            this@ItemOverviewActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().cartDao()

        //Initialising productDao
        productDao = Room.databaseBuilder(
            this@ItemOverviewActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().productDao()

        //Getting bundle from intent passed from ShoppingItemsFragment
        val bundle = intent.getBundleExtra(Constants.SHOPPING_ITEM_BUNDLE)
        //Initialising current product to be displayed
        product = bundle!!.getParcelable(Constants.ITEM_DATA)!!

        //Setting product image on the imageview
        Glide.with(this).load(product.img).into(findViewById(R.id.shopping_item_imageview))

        cartTextView = findViewById(R.id.text_cart)

        //Shows progress bar
        itemOverviewActivityProgress!!.visibility = View.VISIBLE
        //Setting various details of the current product
        findViewById<TextView>(R.id.text_book_name).text = product.name
        findViewById<TextView>(R.id.text_book_author).text = "By ${product.author}"
        findViewById<TextView>(R.id.text_price).text = product.price.toString()
        findViewById<TextView>(R.id.text_image_description).text = product.description

        //Hides progress bar
        itemOverviewActivityProgress!!.visibility = View.GONE
        //Shows different details on the basis whether the product is already added to cart or not
        if (cartDao.exists(product.id, currentUserId)) {
            addToCartIsClicked = true
            cartTextView.text = "GO TO CART"
        }

        //Adding on click listener on various views
        findViewById<View>(R.id.btn_add_to_cart).setOnClickListener(this)
        findViewById<View>(R.id.btn_buy).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_add_to_cart -> {
                    //If add to cart button is not clicked already, then change text and saving order to cart
                    if (!addToCartIsClicked) {
                        saveOrderToCart()
                        cartTextView.text = getString(R.string.go_to_cart_string)
                        addToCartIsClicked = true
                    } else {
                        //Else Starting CartActivity if the current product is already added to cart
                        startActivity(Intent(this@ItemOverviewActivity, CartActivity::class.java))
                        finish()
                    }
                }
                R.id.btn_buy -> {
                    //Deletes product from products table which had been already been bought
                    productDao.deleteProducts(currentUserId)
                    //Inserts current product to products table
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
                    //Starts SelectAddressActivity and finishes current activity
                    val intent =
                        Intent(this@ItemOverviewActivity, SelectAddressActivity::class.java)
                    intent.putExtra(Constants.DIRECT_BUY_STATUS, true)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    /**
     * Adds the current product to the cart
     */
    private fun saveOrderToCart() {
        cartDao.insertOrder(
            CartOrder(
                null,
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