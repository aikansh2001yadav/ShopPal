package com.example.shoppal.models


data class Address(
    /**
     * Stores full name of the person
     */
    val addressFullName: String = "",
    /**
     * Stores phone number of the person
     */
    val addressPhone: String = "",
    /**
     * Stores address details
     */
    val addressDetails: String = "",
    /**
     * Stores pin code of the address
     */
    val addressPinCode: String = "",
    /**
     * Stores additional details of the address
     */
    val addressAdditionalDetails: String = "",
    /**
     * Stores the type of address
     */
    val addressType: String = ""
)