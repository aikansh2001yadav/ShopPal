package com.example.shoppal.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddressDetail(
    /**
     * Auto generate primary key
     */
    @PrimaryKey(autoGenerate = true) val addressId: Long?,
    /**
     * Stores id of the current user
     */
    @ColumnInfo(name = "current_user_id") val currentUserId: String?,
    /**
     * Stores full name of the person
     */
    @ColumnInfo(name = "address_full_name") val addressFullName: String?,
    /**
     * Stores phone number of the person
     */
    @ColumnInfo(name = "address_phone") val addressPhone: String?,
    /**
     * Stores address details of the person
     */
    @ColumnInfo(name = "address_details") val addressDetails: String?,
    /**
     * Stores pin code of the address
     */
    @ColumnInfo(name = "address_pin_code") val addressPinCode: String?,
    /**
     * Stores additional details of the address
     */
    @ColumnInfo(name = "address_additional_details") val addressAdditionalDetails: String?,
    /**
     * Stores type of the address
     */
    @ColumnInfo(name = "address_type") val addressType: String?
) {
    constructor(
        currentUserId: String?,
        addressFullName: String?,
        addressPhone: String?,
        addressDetails: String?,
        addressPinCode: String?,
        addressAdditionalDetails: String?,
        addressType: String?
    ) : this(
        null,
        currentUserId,
        addressFullName,
        addressPhone,
        addressDetails,
        addressPinCode,
        addressAdditionalDetails,
        addressType
    )
}