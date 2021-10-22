package com.example.shoppal.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.shoppal.R
import com.example.shoppal.adapters.AddressDetailsAdapter
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.room.daos.AddressDao
import com.example.shoppal.room.databases.RoomDatabase
import com.example.shoppal.room.entities.AddressDetail
import com.example.shoppal.utils.Constants

class SelectAddressActivity : AppCompatActivity() {
    /**
     * Shows reference of progress bar that shows progress
     */
    private var selectAddressProgressBar: ProgressBar? = null

    /**
     * Shows whether product is bought directly via ItemOverviewActivity
     */
    private var directBuyStatus = false

    /**
     * Stores id of the current user
     */
    private lateinit var currentUserId: String

    /**
     * Stores the reference of addressDao that performs actions on address items
     */
    private lateinit var addressDao: AddressDao

    /**
     * Stores the reference of recyclerview that shows all addresses
     */
    private lateinit var addressRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_activity_address)

        selectAddressProgressBar = findViewById(R.id.activity_select_address_progress)
        //Initialising direct buy status
        directBuyStatus = intent.getBooleanExtra(Constants.DIRECT_BUY_STATUS, false)
        //Initialising addressDao
        addressDao = Room.databaseBuilder(
            this@SelectAddressActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().addressDao()

        addressRecyclerView = findViewById(R.id.recyclerview_address)
        //Adding on click listener on btn_add_address button
        findViewById<View>(R.id.btn_add_address).setOnClickListener {
            startActivity(Intent(this@SelectAddressActivity, AddAddressActivity::class.java))
        }
        //Setting linear layout manager on addressRecyclerView
        addressRecyclerView.layoutManager = LinearLayoutManager(this@SelectAddressActivity)
        //Initialising current user id
        currentUserId = Firebase(this).currentUserId()
    }

    override fun onResume() {
        super.onResume()
        //Shows progress bar
        selectAddressProgressBar!!.visibility = View.VISIBLE
        //Stores all addresses from addressDao in addressDetailsList
        val addressDetailsList = addressDao.getAllAddresses(currentUserId)

        //Hides progress bar
        selectAddressProgressBar!!.visibility = View.GONE
        //Refreshes address details list in the address recycler view
        addressRecyclerView.adapter = AddressDetailsAdapter(
            this@SelectAddressActivity,
            addressDetailsList as ArrayList<AddressDetail>,
            directBuyStatus
        )
    }
}