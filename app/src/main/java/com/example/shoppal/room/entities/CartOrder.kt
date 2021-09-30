package com.example.shoppal.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartOrder(
    @PrimaryKey val orderId: Long,
    @ColumnInfo(name = "current_user_id") val currentUserId: String?,
    @ColumnInfo(name = "item_name") val itemName: String?,
    @ColumnInfo(name = "item_price") val itemPrice: Double?,
    @ColumnInfo(name = "item_image_url") val itemImageUrl: String?,
    @ColumnInfo(name = "item_count") var itemCount :Int
)