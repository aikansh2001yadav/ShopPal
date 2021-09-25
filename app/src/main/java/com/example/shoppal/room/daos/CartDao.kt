package com.example.shoppal.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppal.room.entities.CartOrder

@Dao
interface CartDao {
    @Query("SELECT * FROM cartorder WHERE current_user_id IN (:currentUserId)")
    fun getAllOrders(currentUserId:String):List<CartOrder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(cartOrder: CartOrder)

    @Query("DELETE FROM cartorder WHERE current_user_id IN (:currentUserId)")
    fun deleteOrders(currentUserId: String)

    @Query("SELECT EXISTS (SELECT 1 FROM cartorder WHERE current_user_id IN (:currentUserId) AND orderId IN (:orderId))")
    fun exists(orderId:Long, currentUserId: String): Boolean

    @Query("DELETE FROM cartorder WHERE current_user_id IN (:currentUserId) AND orderId IN (:orderId)")
    fun deleteCurrentOrder(orderId: Long, currentUserId: String)
}