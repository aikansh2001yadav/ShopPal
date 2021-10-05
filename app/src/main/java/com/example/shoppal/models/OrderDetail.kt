package com.example.shoppal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlin.collections.ArrayList

data class OrderDetail(
    val orderId: String = "",
    val orderDate: String = "",
    val orderStatus: String = "",
    val address: Address = Address(),
    val orderReceipt: OrderReceipt = OrderReceipt(),
    val orderArrayList: ArrayList<Order> = ArrayList()
)