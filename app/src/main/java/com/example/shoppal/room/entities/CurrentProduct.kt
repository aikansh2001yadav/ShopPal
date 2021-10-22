package com.example.shoppal.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentProduct (
    /**
     * Stores primary key
     */
    @PrimaryKey
    val orderId: Long,
    /**
     * Stores user id of the current user
     */
    @ColumnInfo(name = "current_user_id") val currentUserId: String?,
    /**
     * Stores name of the item
     */
    @ColumnInfo(name = "item_name") val itemName: String?,
    /**
     * Stores price of the item
     */
    @ColumnInfo(name = "item_price") val itemPrice: Double?,
    /**
     * Stores url of the image of the item
     */
    @ColumnInfo(name = "item_image_url") val itemImageUrl: String?,
    /**
     * Stores no of items
     */
    @ColumnInfo(name = "item_count") var itemCount :Int
)