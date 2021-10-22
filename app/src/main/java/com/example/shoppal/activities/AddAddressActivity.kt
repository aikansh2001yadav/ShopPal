package com.example.shoppal.activities

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.shoppal.R
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.room.daos.AddressDao
import com.example.shoppal.room.databases.RoomDatabase
import com.example.shoppal.room.entities.AddressDetail
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddAddressActivity : AppCompatActivity(), View.OnClickListener {
    /**
     * Stores the reference of address dao
     */
    private var addressDao: AddressDao? = null

    /**
     * Stores the reference of textOtherDetails TextInputLayout which is used to store other details
     */
    private var textOtherDetails: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        //Assigning address dao by room database builder
        addressDao = Room.databaseBuilder(
            this@AddAddressActivity,
            RoomDatabase::class.java,
            "offline_temp_database"
        ).allowMainThreadQueries().build().addressDao()
        //Assigning textOtherDetails TextInputLayout
        textOtherDetails = findViewById(R.id.text_other_details)

        //Adding on click listener on various views
        findViewById<RadioButton>(R.id.btn_home).setOnClickListener(this)
        findViewById<RadioButton>(R.id.btn_office).setOnClickListener(this)
        findViewById<RadioButton>(R.id.btn_other).setOnClickListener(this)
        findViewById<View>(R.id.btn_address_submit).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_home -> {
                    //If btn_home is clicked, set its visibility to be gone
                    textOtherDetails!!.visibility = View.GONE
                }
                R.id.btn_office -> {
                    //If btn_office is clicked, set its visibility to be gone
                    textOtherDetails!!.visibility = View.GONE
                }
                R.id.btn_other -> {
                    //If btn_other is clicked, set its visibility to be visible
                    textOtherDetails!!.visibility = View.VISIBLE
                }
                R.id.btn_address_submit -> {
                    //If btn_address_submit is clicked, then submit details and finish current activity
                    submitDetails()
                    finish()
                }
            }
        }
    }

    /**
     * Submit address details and store the address in room database
     */
    private fun submitDetails() {
        //Getting current user id via firebase
        val currentUserId = Firebase(this).currentUserId()
        //Storing various address details in constant variables
        val fullName = findViewById<TextInputEditText>(R.id.edit_text_address_name).text.toString()
        val phoneNumber =
            findViewById<TextInputEditText>(R.id.edit_text_address_phone).text.toString()
        val addressDetails =
            findViewById<TextInputEditText>(R.id.edit_text_address_details).text.toString()
        val postalCode = findViewById<TextInputEditText>(R.id.edit_text_postal_code).text.toString()
        val additionalDetail =
            findViewById<TextInputEditText>(R.id.edit_text_additional_details).text.toString()
        var addressType = ""

        //Storing address type on the basis of radio button selected
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
        //Stores address in room database
        addressDao!!.insertAddress(
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