package com.example.shoppal.firebase

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.room.Room
import com.example.shoppal.activities.CheckoutActivity
import com.example.shoppal.activities.DashboardActivity
import com.example.shoppal.models.OrderDetail
import com.example.shoppal.room.databases.RoomDatabase
import com.google.firebase.database.FirebaseDatabase

class OrderCheckoutDatabase(private val context: Context) {
    private val databaseInstance =
        FirebaseDatabase.getInstance("https://shoppal-42b45-default-rtdb.asia-southeast1.firebasedatabase.app")

    fun submitOrderToRealtimeDatabase(
        orderId: String,
        currentUserId: String,
        orderDetail: OrderDetail
    ) {
        val cartDao = Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().cartDao()
        val productDao = Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().productDao()
        databaseInstance.reference.child("orders").child(currentUserId).child(orderId)
            .setValue(orderDetail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Order placed", Toast.LENGTH_SHORT)
                        .show()
                    cartDao.deleteOrders(currentUserId)
                    productDao.deleteProducts(currentUserId)
                    val intent = Intent(context, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Error: Order not placed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}