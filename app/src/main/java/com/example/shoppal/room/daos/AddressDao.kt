package com.example.shoppal.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppal.room.entities.AddressDetail

@Dao
interface AddressDao {
    @Query("SELECT * FROM addressdetail WHERE current_user_id IN (:currentUserId)")
    fun getAllAddresses(currentUserId: String): List<AddressDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(addressDetail: AddressDetail)

    @Query("DELETE FROM addressdetail WHERE current_user_id IN (:currentUserId)")
    fun deleteAddresses(currentUserId: String)

    @Query("DELETE FROM addressdetail WHERE current_user_id IN (:currentUserId) AND addressId IN (:addressId)")
    fun deleteCurrentAddress(addressId: Long, currentUserId: String)

    @Query("SELECT * FROM addressdetail WHERE current_user_id IN (:currentUserId) AND addressId IN (:addressId)")
    fun getCheckoutAddressDetail(addressId: Long, currentUserId: String) : List<AddressDetail>
}