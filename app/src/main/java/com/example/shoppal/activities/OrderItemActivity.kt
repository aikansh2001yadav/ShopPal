package com.example.shoppal.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppal.R
import com.example.shoppal.adapters.OrderAdapter
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.firebase.ProductItemDatabase
import com.example.shoppal.models.OrderDetail
import com.example.shoppal.utils.Constants
import java.text.SimpleDateFormat

class OrderItemActivity : AppCompatActivity() {
    /**
     * Stores reference of progress bar that shows progress
     */
    private var orderItemProgress: ProgressBar? = null

    //Stores an instance of ProductItemDatabase
    private val productItemDatabase = ProductItemDatabase(this@OrderItemActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_item)

        orderItemProgress = findViewById(R.id.activity_order_item_progress)
        //Stores id of the current user
        val currentUserId = Firebase(this).currentUserId()
        //Stores order id of the current order
        val orderId = intent.getStringExtra(Constants.ORDER_ITEM)

        //Shows progress bar
        orderItemProgress!!.visibility = View.VISIBLE
        //Populates all product items in the recyclerview which are in current order
        productItemDatabase.getOrder(orderId!!, currentUserId)
    }

    /**
     * Populates all views by inserting order's details
     */
    fun populateActivity(orderItem: OrderDetail) {
        findViewById<TextView>(R.id.text_order_activity_id).text = orderItem.orderId
        val simpleDateFormat = SimpleDateFormat("d MMM, yyyy hh:mm a")
        val orderDate: String = simpleDateFormat.format(orderItem.orderDate!!)
        findViewById<TextView>(R.id.text_order_activity_order_date).text = orderDate
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
        //Hides progress bar
        orderItemProgress!!.visibility = View.GONE
    }
}