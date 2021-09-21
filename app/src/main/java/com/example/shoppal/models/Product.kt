package com.example.shoppal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product(
    val author: String = "",
    val category: String = "",
    val description: String = "",
    val id: Long = 0,
    val img: String = "",
    val inCart: Boolean = false,
    val name: String = "",
    val price: Double = 0.0,
    val type: String = ""
):Parcelable