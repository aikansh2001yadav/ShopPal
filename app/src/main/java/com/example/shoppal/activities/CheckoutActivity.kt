package com.example.shoppal.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    private lateinit var checkoutAddressDetail: AddressDetail
    private lateinit var currentUserId: String
    private lateinit var checkoutProductsList: ArrayList<CartOrder>
    private lateinit var checkoutProductsDB: RoomDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val addressId = intent.getLongExtra(Constants.ADDRESS_ID, 0)
        currentUserId = Firebase(this@CheckoutActivity).currentUserId()
        val checkoutProductsRecyclerView =
            findViewById<RecyclerView>(R.id.recyclerview_checkout_items)
        checkoutProductsDB = Room.databaseBuilder(
            this@CheckoutActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build()
        val checkoutProductsDao = checkoutProductsDB.cartDao()
        checkoutProductsList =
            checkoutProductsDao.getAllOrders(currentUserId) as ArrayList<CartOrder>
        checkoutProductsRecyclerView.apply {
            this.adapter = CheckoutProductsAdapter(
                this@CheckoutActivity,
                checkoutProductsList
            )
            this.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        }

        val checkoutAddressDao = checkoutProductsDB.addressDao()
        checkoutAddressDetail =
            checkoutAddressDao.getCheckoutAddressDetail(addressId, currentUserId)[0]
        findViewById<TextView>(R.id.text_checkout_address_type).text =
            checkoutAddressDetail.addressType
        findViewById<TextView>(R.id.text_checkout_full_name).text =
            checkoutAddressDetail.addressFullName
        findViewById<TextView>(R.id.text_checkout_full_address).text =
            checkoutAddressDetail.addressDetails + "," + checkoutAddressDetail.addressPinCode
        findViewById<TextView>(R.id.text_checkout_phone).text =
            checkoutAddressDetail.addressPhone

        var subTotal = 0.0
        for (checkoutProduct in checkoutProductsList) {
            subTotal += checkoutProduct.itemCount * checkoutProduct.itemPrice!!
        }
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
                    placeOrder()
                }
            }
        }
    }

    private fun placeOrder() {
        val currentUserId = checkoutAddressDetail.currentUserId!!
        val orderId = UUID.randomUUID().toString()
        val productArrayList = arrayListOf<Order>()
        var subTotal = 0.0
        for (checkoutProduct in checkoutProductsList) {
            subTotal += checkoutProduct.itemCount * checkoutProduct.itemPrice!!
            productArrayList.add(Order(checkoutProduct.orderId, checkoutProduct.itemCount))
        }
        val address = Address(
            checkoutAddressDetail.addressFullName!!,
            checkoutAddressDetail.addressPhone!!,
            checkoutAddressDetail.addressDetails!!,
            checkoutAddressDetail.addressPinCode!!,
            checkoutAddressDetail.addressAdditionalDetails!!,
            checkoutAddressDetail.addressType!!
        )
        val orderDetail = OrderDetail(
            orderId,
            address,
            OrderReceipt(
                String.format("%.2f", abs(subTotal)),
                "15.00",
                String.format("%.2f", abs(subTotal) + 15.00),
                "Cash On Delivery"
            ),
            productArrayList
        )
        OrderCheckoutDatabase(this@CheckoutActivity).submitOrderToRealtimeDatabase(
            orderId,
            currentUserId,
            orderDetail
        )
    }

    override fun onBackPressed() {
        startActivity(Intent(this, SelectAddressActivity::class.java))
        super.onBackPressed()
    }
}