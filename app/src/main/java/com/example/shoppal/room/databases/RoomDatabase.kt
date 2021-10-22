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
    /**
     * Stores abstract class of cartDao that performs actions on all cart orders
     */
    abstract fun cartDao() : CartDao

    /**
     * Stores abstract class of addressDao that performs actions on all addresses
     */
    abstract fun addressDao() : AddressDao

    /**
     * Stores abstract class of productDao that performs actions on all products
     */
    abstract fun productDao() : ProductDao
}