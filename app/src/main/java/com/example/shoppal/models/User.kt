package com.example.shoppal.models

data class User(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val profileImage: String,
    val mobileNumber: String,
    val gender: String,
    val isProfileCompleted: Int
)