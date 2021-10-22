package com.example.shoppal.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppal.room.entities.CurrentProduct

@Dao
interface ProductDao {
    /**
     * Returns products related to the current user
     */
    @Query("SELECT * FROM currentproduct WHERE current_user_id IN (:currentUserId)")
    fun getProducts(currentUserId:String) : List<CurrentProduct>

    /**
     * Inserts product in the room database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product:CurrentProduct)

    /**
     * Deletes all products related to the current user
     */
    @Query("DELETE FROM currentproduct WHERE current_user_id IN (:currentUserId)")
    fun deleteProducts(currentUserId: String)
}