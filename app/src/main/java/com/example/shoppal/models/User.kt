package com.example.shoppal.models

/**
 * Data model of user containing all the user profile information
 */
class User(
    /**
     * Stores id of the user
     */
    val id: String = "",
    /**
     * Stores name of the user
     */
    val name: String = "",
    /**
     * Stores last name of the user
     */
    val lastName: String = "",
    /**
     * Stores email address of the user
     */
    val email: String = "",
    /**
     * Stores url address of profile image
     */
    val profileImage: String = "",
    /**
     * Stores mobile number of the user
     */
    val mobileNumber: String = "",
    /**
     * Stores gender of the user
     */
    val gender: String = "",
    /**
     * Stores status of profile competition
     */
    val profileCompleted: Int = 0
)