package com.example.shoppal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*
import kotlin.collections.ArrayList

data class OrderDetail(
    /**
     * Stores id of the order
     */
    val orderId: String = "",
    /**
     * Stores date at which the order has been placed
     */
    val orderDate: Date? = null,
    /**
     * Stores status of the order
     */
    val orderStatus: String = "",
    /**
     * Stores reference of address
     */
    val address: Address = Address(),
    /**
     * Stores reference of order receipt of the given order detail
     */
    val orderReceipt: OrderReceipt = OrderReceipt(),
    /**
     * Stores an arraylist of orders
     */
    val orderArrayList: ArrayList<Order> = ArrayList()
)