package com.example.shoppal.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddressDetail(
    @PrimaryKey(autoGenerate = true) val addressId: Long?,
    @ColumnInfo(name = "current_user_id") val currentUserId: String?,
    @ColumnInfo(name = "address_full_name") val addressFullName: String?,
    @ColumnInfo(name = "address_phone") val addressPhone: String?,
    @ColumnInfo(name = "address_details") val addressDetails: String?,
    @ColumnInfo(name = "address_pin_code") val addressPinCode: String?,
    @ColumnInfo(name = "address_additional_details") val addressAdditionalDetails: String?,
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