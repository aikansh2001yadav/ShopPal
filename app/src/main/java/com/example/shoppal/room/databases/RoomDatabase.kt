package com.example.shoppal.room.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppal.room.daos.AddressDao
import com.example.shoppal.room.daos.CartDao
import com.example.shoppal.room.daos.ProductDao
import com.example.shoppal.room.entities.AddressDetail
import com.example.shoppal.room.entities.CartOrder
import com.example.shoppal.room.entities.CurrentProduct

@Database(entities = arrayOf(CartOrder::class, AddressDetail::class, CurrentProduct::class), version = 1)
abstract class RoomDatabase :RoomDatabase(){
    abstract fun cartDao() : CartDao
    abstract fun addressDao() : AddressDao
    abstract fun productDao() : ProductDao
}