package com.example.shoppal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.room.Room
import com.example.shoppal.R
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.room.daos.AddressDao
import com.example.shoppal.room.databases.RoomDatabase
import com.example.shoppal.room.entities.AddressDetail
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddAddressActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var addressDao: AddressDao
    private lateinit var textOtherDetails: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        addressDao = Room.databaseBuilder(
            this@AddAddressActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().addressDao()
        textOtherDetails = findViewById(R.id.text_other_details)
        findViewById<RadioButton>(R.id.btn_home).setOnClickListener(this)
        findViewById<RadioButton>(R.id.btn_office).setOnClickListener(this)
        findViewById<RadioButton>(R.id.btn_other).setOnClickListener(this)
        findViewById<View>(R.id.btn_address_submit).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_home -> {
                    textOtherDetails.visibility = View.GONE
                }
                R.id.btn_office -> {
                    textOtherDetails.visibility = View.GONE
                }
                R.id.btn_other -> {
                    textOtherDetails.visibility = View.VISIBLE
                }
                R.id.btn_address_submit -> {
                    submitDetails()
                    finish()
                }
            }
        }
    }

    private fun submitDetails() {
        val currentUserId = Firebase(this).currentUserId()
        val fullName = findViewById<TextInputEditText>(R.id.edit_text_address_name).text.toString()
        val phoneNumber =
            findViewById<TextInputEditText>(R.id.edit_text_address_phone).text.toString()
        val addressDetails =
            findViewById<TextInputEditText>(R.id.edit_text_address_details).text.toString()
        val postalCode = findViewById<TextInputEditText>(R.id.edit_text_postal_code).text.toString()
        val additionalDetail =
            findViewById<TextInputEditText>(R.id.edit_text_additional_details).text.toString()
        var addressType = ""
        when (findViewById<RadioGroup>(R.id.radio_group_address_type).checkedRadioButtonId) {
            R.id.btn_home -> {
                addressType = "home"
            }
            R.id.btn_office -> {
                addressType = "office"
            }
            R.id.btn_other -> {
                addressType =
                    findViewById<TextInputEditText>(R.id.edit_text_other_details).text.toString()
            }
        }
        addressDao.insertAddress(
            AddressDetail(
                currentUserId,
                fullName,
                phoneNumber,
                addressDetails,
                postalCode,
                additionalDetail,
                addressType
            )
        )
    }
}