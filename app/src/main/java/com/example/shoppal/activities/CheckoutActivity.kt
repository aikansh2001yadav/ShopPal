package com.example.shoppal.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.shoppal.R
import com.example.shoppal.adapters.CheckoutProductsAdapter
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.firebase.OrderCheckoutDatabase
import com.example.shoppal.models.Address
import com.example.shoppal.models.Order
import com.example.shoppal.models.OrderDetail
import com.example.shoppal.models.OrderReceipt
import com.example.shoppal.room.databases.RoomDatabase
import com.example.shoppal.room.entities.AddressDetail
import com.example.shoppal.room.entities.CartOrder
import com.example.shoppal.utils.Constants
import java.util.*
import kotlin.math.abs

class CheckoutActivity : AppCompatActivity(), View.OnClickListener {
    /**
     * Stores whether user is buying the product directly or via cart
     */
    private var directBuyStatus = false

    /**
     * Stores current user id of the user
     */
    private var currentUserId = ""

    /**
     * Stores address detail of the address selected in SelectAddressActivity
     */
    private var checkoutAddressDetail: AddressDetail? = null

    /**
     * Stores all products to be bought in checkoutProductsList
     */
    private var checkoutProductsList = ArrayList<CartOrder>()

    /**
     * Stores the reference of room database
     */
    private var checkoutProductsDB: RoomDatabase? = null

    /**
     * Stores the reference of checkout progress bar that shows progress
     */
    private var checkoutProgressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        checkoutProgressBar = findViewById(R.id.activity_checkout_progress)
        //Assigning direct buy status
        directBuyStatus = intent.getBooleanExtra(Constants.DIRECT_BUY_STATUS, false)
        //Storing address id of the address selected in SelectAddressActivity
        val addressId = intent.getLongExtra(Constants.ADDRESS_ID, 0)
        //Storing current user id of the user
        currentUserId = Firebase(this@CheckoutActivity).currentUserId()
        //Stores the reference of checkoutProductsRecyclerView which will show all products to be bought
        val checkoutProductsRecyclerView =
            findViewById<RecyclerView>(R.id.recyclerview_checkout_items)


        //Assigning Room database reference
        checkoutProductsDB = Room.databaseBuilder(
            this@CheckoutActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build()

        //Stores the reference of cart dao that deals with products in cart
        val checkoutProductsDao = checkoutProductsDB!!.cartDao()
        //Stores the reference of product dao that deals with products not in cart
        val currentProductDao = checkoutProductsDB!!.productDao()

        //Adding product to be bought directly via buy option in ItemOverviewActivity
        if (directBuyStatus) {
            val currentProduct = currentProductDao.getProducts(currentUserId)[0]
            checkoutProductsList.add(
                CartOrder(
                    currentProduct.orderId,
                    currentUserId,
                    currentProduct.itemName,
                    currentProduct.itemPrice,
                    currentProduct.itemImageUrl,
                    1
                )
            )
        } else {
            //Shows progress bar
            checkoutProgressBar!!.visibility = View.VISIBLE
            //Adding all cart orders to be bought via checkout option in CartActivity
            checkoutProductsList =
                checkoutProductsDao.getAllOrders(currentUserId) as ArrayList<CartOrder>
            //Hides progress bar
            checkoutProgressBar!!.visibility = View.GONE
        }
        //Shows all products to be bought in the recycler view
        checkoutProductsRecyclerView.apply {
            this.adapter = CheckoutProductsAdapter(
                this@CheckoutActivity,
                checkoutProductsList
            )
            this.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        }
        //Stores the reference of addressDao
        val checkoutAddressDao = checkoutProductsDB!!.addressDao()
        //Assigning checkout address detail selected in SelectAddressActivity
        checkoutAddressDetail =
            checkoutAddressDao.getCheckoutAddressDetail(addressId, currentUserId)[0]

        //Setting various address details
        if (checkoutAddressDetail != null) {
            findViewById<TextView>(R.id.text_checkout_address_type).text =
                checkoutAddressDetail!!.addressType
            findViewById<TextView>(R.id.text_checkout_full_name).text =
                checkoutAddressDetail!!.addressFullName
            findViewById<TextView>(R.id.text_checkout_full_address).text =
                checkoutAddressDetail!!.addressDetails + "," + checkoutAddressDetail!!.addressPinCode
            findViewById<TextView>(R.id.text_checkout_phone).text =
                checkoutAddressDetail!!.addressPhone
        }

        //Calculates total cost of products to be bought
        var subTotal = 0.0
        for (checkoutProduct in checkoutProductsList) {
            subTotal += checkoutProduct.itemCount * checkoutProduct.itemPrice!!
        }

        //Setting order receipt details
        findViewById<TextView>(R.id.text_checkout_order_subtotal).text =
            "$${String.format("%.2f", abs(subTotal))}"
        findViewById<TextView>(R.id.text_checkout_charge).text = "$15.00"
        findViewById<TextView>(R.id.text_checkout_total_amount).text =
            "$${String.format("%.2f", abs(subTotal) + 15.00)}"
        findViewById<View>(R.id.btn_place_order).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_place_order -> {
                    //Uploads order to firebase realtime database
                    placeOrder()
                }
            }
        }
    }

    /**
     * Places order and stores all related details on firebase realtime database
     */
    private fun placeOrder() {
        //Shows progress bar
        checkoutProgressBar!!.visibility = View.VISIBLE
        //Getting all address details and storing in constant variables
        val currentUserId = checkoutAddressDetail!!.currentUserId
        val orderId = UUID.randomUUID().toString()
        val orderDate = Date()

        //Calculating total cost and adding all the order items in productArrayList
        val productArrayList = arrayListOf<Order>()
        var subTotal = 0.0
        for (checkoutProduct in checkoutProductsList) {
            subTotal += checkoutProduct.itemCount * checkoutProduct.itemPrice!!
            productArrayList.add(
                Order(
                    checkoutProduct.orderId,
                    checkoutProduct.itemName!!,
                    checkoutProduct.itemPrice,
                    checkoutProduct.itemImageUrl!!,
                    checkoutProduct.itemCount
                )
            )
        }

        //Stores the address detail of the current order
        val address = Address(
            checkoutAddressDetail!!.addressFullName!!,
            checkoutAddressDetail!!.addressPhone!!,
            checkoutAddressDetail!!.addressDetails!!,
            checkoutAddressDetail!!.addressPinCode!!,
            checkoutAddressDetail!!.addressAdditionalDetails!!,
            checkoutAddressDetail!!.addressType!!
        )
        val orderDetail = OrderDetail(
            orderId,
            orderDate,
            "Pending",
            address,
            OrderReceipt(
                String.format("%.2f", abs(subTotal)),
                "15.00",
                String.format("%.2f", abs(subTotal) + 15.00),
                "Cash On Delivery"
            ),
            productArrayList
        )
        //Uploads all the order details on the realtime database
        OrderCheckoutDatabase(this@CheckoutActivity).submitOrderToRealtimeDatabase(
            orderId,
            currentUserId!!,
            orderDetail, directBuyStatus
        )
    }

    /**
     * Hides progress bar
     */
    fun hideProgressBar() {
        checkoutProgressBar!!.visibility = View.GONE
    }
}