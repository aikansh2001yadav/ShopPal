package com.example.shoppal.activities

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppal.R
import com.example.shoppal.utils.Constants

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        setUserProfileDetails()
    }


    private fun setUserProfileDetails() {
        val sharedPreferences = this.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        //TODO add capability to add profile image
        val firstName = sharedPreferences.getString(Constants.NAME, "")
        val lastName = sharedPreferences.getString(Constants.LAST_NAME, "")
        findViewById<EditText>(R.id.edit_text_profile_name).setText("$firstName $lastName")
        findViewById<EditText>(R.id.edit_text_profile_email).setText(sharedPreferences.getString(Constants.EMAIL, ""))
        findViewById<EditText>(R.id.edit_text_profile_phone).setText(sharedPreferences.getString(Constants.MOBILE_NUMBER, ""))
        val gender = sharedPreferences.getString(Constants.GENDER, "")
        if(gender.equals(Constants.MALE)){
            findViewById<RadioButton>(R.id.btn_male).isChecked = true
        }else if(gender.equals(Constants.FEMALE)){
            findViewById<RadioButton>(R.id.btn_female).isChecked = true
        }
    }
}