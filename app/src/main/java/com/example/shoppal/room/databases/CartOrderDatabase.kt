package com.example.shoppal.room.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppal.room.daos.CartDao
import com.example.shoppal.room.entities.CartOrder

@Database(entities = arrayOf(CartOrder::class), version = 1)
abstract class CartOrderDatabase :RoomDatabase(){
    abstract fun cartDao() : CartDao
}