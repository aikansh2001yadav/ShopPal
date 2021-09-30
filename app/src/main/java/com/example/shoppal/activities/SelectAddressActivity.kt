package com.example.shoppal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.shoppal.R
import com.example.shoppal.adapters.AddressDetailsAdapter
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.room.daos.AddressDao
import com.example.shoppal.room.databases.RoomDatabase
import com.example.shoppal.room.entities.AddressDetail

class SelectAddressActivity : AppCompatActivity() {
    private lateinit var currentUserId: String
    private lateinit var addressDao: AddressDao
    private lateinit var addressRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_activity_address)

        addressDao = Room.databaseBuilder(
            this@SelectAddressActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().addressDao()
        addressRecyclerView = findViewById(R.id.recyclerview_address)
        findViewById<View>(R.id.btn_add_address).setOnClickListener {
            startActivity(Intent(this@SelectAddressActivity, AddAddressActivity::class.java))
        }
        addressRecyclerView.layoutManager = LinearLayoutManager(this@SelectAddressActivity)
        currentUserId = Firebase(this).currentUserId()
    }

    override fun onResume() {
        super.onResume()
        val addressDetailsList = addressDao.getAllAddresses(currentUserId)

        addressRecyclerView.adapter = AddressDetailsAdapter(
            this@SelectAddressActivity,
            addressDetailsList as ArrayList<AddressDetail>
        )
    }
}