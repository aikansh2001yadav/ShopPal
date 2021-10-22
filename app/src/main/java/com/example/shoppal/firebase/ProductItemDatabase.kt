package com.example.shoppal.firebase

import com.example.shoppal.activities.OrderItemActivity
import com.example.shoppal.models.OrderDetail
import com.example.shoppal.models.Product
import com.example.shoppal.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue

class ProductItemDatabase(private val orderItemActivity: OrderItemActivity) {
    /**
     * Stores reference of databaseInstance
     */
    private val databaseInstance =
        FirebaseDatabase.getInstance("https://shoppal-42b45-default-rtdb.asia-southeast1.firebasedatabase.app")

    /**
     * Gets order detail of the selected order and then update UI
     */
    fun getOrder(orderId: String, currentUserId: String) {
            databaseInstance.getReference(Constants.ORDERS).child(currentUserId).orderByKey()
                .equalTo(orderId).get().addOnSuccessListener {
                for (childSnapshot in it.children) {
                    val orderItem = childSnapshot.getValue<OrderDetail>()!!
                    orderItemActivity.populateActivity(orderItem)
                }
            }
    }
}