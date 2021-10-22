package com.example.shoppal.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartOrder(
    /**
     * Auto generates order id
     */
    @PrimaryKey(autoGenerate = true) val primaryKey: Long?,
    /**
     * Stores the order id of the product
     */
    @ColumnInfo(name = "orderId") val orderId: Long,
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
     * Stores url of the image
     */
    @ColumnInfo(name = "item_image_url") val itemImageUrl: String?,
    /**
     * Stores no of items
     */
    @ColumnInfo(name = "item_count") var itemCount: Int
) {
    constructor(
        orderId: Long,
        currentUserId: String?,
        itemName: String?,
        itemPrice: Double?,
        itemImageUrl: String?,
        itemCount: Int
    ) : this(
        null,
        orderId,
        currentUserId,
        itemName,
        itemPrice,
        itemImageUrl,
        itemCount
    )
}