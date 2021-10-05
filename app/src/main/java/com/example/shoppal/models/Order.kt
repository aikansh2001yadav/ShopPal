package com.example.shoppal.models

data class Order(
    val id: Long = 0,
    val name: String = "",
    val price: Double = 0.0,
    val img: String = "",
    val orderCount: Int = 0
)