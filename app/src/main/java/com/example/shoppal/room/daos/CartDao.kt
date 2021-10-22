package com.example.shoppal.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppal.room.entities.CartOrder

@Dao
interface CartDao {
    /**
     * Returns all cart orders from the current user
     */
    @Query("SELECT * FROM cartorder WHERE current_user_id IN (:currentUserId)")
    fun getAllOrders(currentUserId:String):List<CartOrder>

    /**
     * Inserts cart order in the room database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(cartOrder: CartOrder)

    /**
     * Delete all orders related to the current user
     */
    @Query("DELETE FROM cartorder WHERE current_user_id IN (:currentUserId)")
    fun deleteOrders(currentUserId: String)

    /**
     * Checks whether the current product exists in the room database
     */
    @Query("SELECT EXISTS (SELECT 1 FROM cartorder WHERE current_user_id IN (:currentUserId) AND orderId IN (:orderId))")
    fun exists(orderId:Long, currentUserId: String): Boolean

    /**
     * Deletes the current product from the room database
     */
    @Query("DELETE FROM cartorder WHERE current_user_id IN (:currentUserId) AND orderId IN (:orderId)")
    fun deleteCurrentOrder(orderId: Long, currentUserId: String)
}