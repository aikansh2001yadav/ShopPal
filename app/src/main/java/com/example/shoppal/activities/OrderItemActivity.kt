package com.example.shoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R
import com.example.shoppal.adapters.OrderAdapter
import com.example.shoppal.adapters.OrderItemsAdapter
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.firebase.ProductItemDatabase
import com.example.shoppal.models.OrderDetail
import com.example.shoppal.models.Product
import com.example.shoppal.utils.Constants

class OrderItemActivity : AppCompatActivity() {
    private val productItemDatabase = ProductItemDatabase(this@OrderItemActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_item)

        val currentUserId = Firebase(this).currentUserId()
        val orderId = intent.getStringExtra(Constants.ORDER_ITEM)

        productItemDatabase.getOrder(orderId!!, currentUserId)
    }

    fun populateActivity(orderItem: OrderDetail) {
        findViewById<TextView>(R.id.text_order_activity_id).text = orderItem.orderId
        findViewById<TextView>(R.id.text_order_activity_order_date).text = orderItem.orderDate
        findViewById<TextView>(R.id.text_order_activity_status).text = orderItem.orderStatus

        val orderItemRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_order_items)
        orderItemRecyclerView.apply {
            this.adapter =
                OrderAdapter(this@OrderItemActivity, orderItem.orderArrayList)
            this.layoutManager = LinearLayoutManager(this@OrderItemActivity)
        }

        findViewById<TextView>(R.id.text_order_address_type).text = orderItem.address.addressType
        findViewById<TextView>(R.id.text_order_address_full_name).text =
            orderItem.address.addressFullName
        findViewById<TextView>(R.id.text_order_full_address).text =
            "${orderItem.address.addressDetails}, ${orderItem.address.addressPinCode}"
        findViewById<TextView>(R.id.text_order_phone).text = orderItem.address.addressPhone

        findViewById<TextView>(R.id.text_order_activity_subtotal).text =
            orderItem.orderReceipt.itemSubTotal
        findViewById<TextView>(R.id.text_order_shipping_charge).text =
            orderItem.orderReceipt.itemShippingCharge
        findViewById<TextView>(R.id.text_order_activity_total_cost).text =
            orderItem.orderReceipt.itemTotalAmount

        findViewById<TextView>(R.id.text_order_payment_mode).text =
            orderItem.orderReceipt.paymentMode
    }
}