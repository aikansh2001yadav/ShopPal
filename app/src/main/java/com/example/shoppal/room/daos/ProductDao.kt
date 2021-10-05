package com.example.shoppal.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppal.room.entities.CurrentProduct

@Dao
interface ProductDao {
    @Query("SELECT * FROM currentproduct WHERE current_user_id IN (:currentUserId)")
    fun getProducts(currentUserId:String) : List<CurrentProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product:CurrentProduct)

    @Query("DELETE FROM currentproduct WHERE current_user_id IN (:currentUserId)")
    fun deleteProducts(currentUserId: String)
}