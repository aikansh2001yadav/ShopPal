package com.example.shoppal.models

data class OrderReceipt(
    /**
     * Stores total price of the orders
     */
    val itemSubTotal: String = "",
    /**
     * Stores shipping charge for the item
     */
    val itemShippingCharge: String = "",
    /**
     * Stores total price of the orders including shipping charge for the item
     */
    val itemTotalAmount: String = "",
    /**
     * Stores type of payment mode for the order
     */
    val paymentMode: String = ""
)