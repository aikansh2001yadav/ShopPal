package com.example.shoppal.models

data class OrderReceipt(
    val itemSubTotal: String = "",
    val itemShippingCharge: String = "",
    val itemTotalAmount: String = "",
    val paymentMode: String = ""
)