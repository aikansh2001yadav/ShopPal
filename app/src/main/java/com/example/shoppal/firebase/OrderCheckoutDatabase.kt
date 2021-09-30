package com.example.shoppal.firebase

import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.shoppal.activities.CheckoutActivity
import com.example.shoppal.models.OrderDetail
import com.example.shoppal.models.Product
import com.example.shoppal.room.databases.RoomDatabase
import com.example.shoppal.utils.Constants
import com.example.shoppal.utils.Tags
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class OrderCheckoutDatabase(private val checkoutActivity: CheckoutActivity) {
    private val databaseInstance =
        FirebaseDatabase.getInstance("https://shoppal-42b45-default-rtdb.asia-southeast1.firebasedatabase.app")

    fun submitOrderToRealtimeDatabase(
        orderId: String,
        currentUserId: String,
        orderDetail: OrderDetail
    ) {
        val cartDao = Room.databaseBuilder(
            checkoutActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().cartDao()
        databaseInstance.reference.child("orders").child(currentUserId).child(orderId)
            .setValue(orderDetail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(checkoutActivity, "Order placed", Toast.LENGTH_SHORT)
                        .show()
                    cartDao.deleteOrders(currentUserId)
                    checkoutActivity.finish()
                } else {
                    Toast.makeText(checkoutActivity, "Error: Order not placed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}