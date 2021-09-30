package com.example.shoppal.models

data class OrderDetail(
    val orderId:String = "",
    val address: Address = Address(),
    val orderReceipt: OrderReceipt = OrderReceipt(),
    val orderArrayList:ArrayList<Order> = ArrayList()
)