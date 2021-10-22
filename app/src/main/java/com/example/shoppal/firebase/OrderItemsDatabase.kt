package com.example.shoppal.firebase

import android.util.Log
import com.example.shoppal.fragments.OrdersFragment
import com.example.shoppal.models.OrderDetail
import com.example.shoppal.utils.Constants
import com.example.shoppal.utils.Tags
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class OrderItemsDatabase(private val ordersFragment:OrdersFragment) {
    /**
     * Stores reference of database instance
     */
    private val databaseInstance =
        FirebaseDatabase.getInstance("https://shoppal-42b45-default-rtdb.asia-southeast1.firebasedatabase.app")

    /**
     * Reads all order details of the current user from realtime database and then update UI
     */
    fun readOrders(currentUserId: String) {
        val databaseReference =
            databaseInstance.getReference(Constants.ORDERS).child(currentUserId)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val orderDetailList = ArrayList<OrderDetail>()
                for (childSnapshot in dataSnapshot.children) {
                    orderDetailList.add(childSnapshot.getValue<OrderDetail>()!!)
                    Log.d(Tags.READ_ORDER_STATUS, "order id: ${childSnapshot.key}")
                }
                orderDetailList.sortByDescending { it.orderDate }
                ordersFragment.updateUI(orderDetailList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(Tags.READ_ORDER_STATUS, "Failed to read value.", error.toException())
            }
        })
    }
}