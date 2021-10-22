package com.example.shoppal.models

data class Order(
    /**
     * Stores id of the order
     */
    val id: Long = 0,
    /**
     * Stores  name of the order
     */
    val name: String = "",
    /**
     * Stores price of the order
     */
    val price: Double = 0.0,
    /**
     * Stores image url of the order
     */
    val img: String = "",
    /**
     * Stores the number of orders
     */
    val orderCount: Int = 0
)