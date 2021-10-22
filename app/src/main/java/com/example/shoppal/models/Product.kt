package com.example.shoppal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product(
    /**
     * Stores name of the author for the product
     */
    val author: String = "",
    /**
     * Stores category of the product
     */
    val category: String = "",
    /**
     * Stores description of the product
     */
    val description: String = "",
    /**
     * Stores id of the product
     */
    val id: Long = 0,
    /**
     * Stores image url of the product
     */
    val img: String = "",
    /**
     * Stores whether product is in cart
     */
    val inCart: Boolean = false,
    /**
     * Stores name of the product
     */
    val name: String = "",
    /**
     * Stores price of the product
     */
    val price: Double = 0.0,
    /**
     * Stores type of the product
     */
    val type: String = ""
):Parcelable