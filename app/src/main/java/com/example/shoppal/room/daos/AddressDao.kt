package com.example.shoppal.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppal.room.entities.AddressDetail

@Dao
interface AddressDao {
    /**
     * Returns all the address details of the current user
     */
    @Query("SELECT * FROM addressdetail WHERE current_user_id IN (:currentUserId)")
    fun getAllAddresses(currentUserId: String): List<AddressDetail>

    /**
     * Inserts address in the room database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(addressDetail: AddressDetail)

    /**
     * Deletes all addresses from the room database
     */
    @Query("DELETE FROM addressdetail WHERE current_user_id IN (:currentUserId)")
    fun deleteAddresses(currentUserId: String)

    /**
     * Deletes current address from the room database
     */
    @Query("DELETE FROM addressdetail WHERE current_user_id IN (:currentUserId) AND addressId IN (:addressId)")
    fun deleteCurrentAddress(addressId: Long, currentUserId: String)

    /**
     * Returns a particular address detail of the current user
     */
    @Query("SELECT * FROM addressdetail WHERE current_user_id IN (:currentUserId) AND addressId IN (:addressId)")
    fun getCheckoutAddressDetail(addressId: Long, currentUserId: String) : List<AddressDetail>
}