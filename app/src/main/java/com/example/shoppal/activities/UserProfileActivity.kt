package com.example.shoppal.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppal.R
import com.example.shoppal.firebase.Firestore
import com.example.shoppal.models.User
import com.example.shoppal.utils.Constants

class UserProfileActivity : AppCompatActivity() {
    private var currentUserId:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //Sets user profile details
        setUserProfileDetails()
        findViewById<View>(R.id.btn_profile_submit).setOnClickListener {
            submitDetails()
        }
    }

    /**
     * Sets user profile details from the sharedPreferences
     */
    private fun setUserProfileDetails() {
        val sharedPreferences = this.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        //TODO add capability to add profile image
        currentUserId = sharedPreferences.getString(Constants.ID, "")!!
        findViewById<EditText>(R.id.edit_text_profile_name).setText(
            sharedPreferences.getString(
                Constants.NAME,
                ""
            )
        )
        findViewById<EditText>(R.id.edit_text_profile_last_name).setText(
            sharedPreferences.getString(
                Constants.LAST_NAME,
                ""
            )
        )
        findViewById<EditText>(R.id.edit_text_profile_email).setText(
            sharedPreferences.getString(
                Constants.EMAIL,
                ""
            )
        )
        findViewById<EditText>(R.id.edit_text_profile_phone).setText(
            sharedPreferences.getString(
                Constants.MOBILE_NUMBER,
                ""
            )
        )
        val gender = sharedPreferences.getString(Constants.GENDER, "")
        if (gender.equals(Constants.MALE)) {
            findViewById<RadioButton>(R.id.btn_male).isChecked = true
        } else if (gender.equals(Constants.FEMALE)) {
            findViewById<RadioButton>(R.id.btn_female).isChecked = true
        }
    }

    /**
     * Validates input information given by the user and give suggestions
     */
    private fun validateDetails(): Boolean {
        return when {
            findViewById<EditText>(R.id.edit_text_profile_name).text.toString().isEmpty() -> {
                Toast.makeText(this@UserProfileActivity, "Please enter name", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            findViewById<EditText>(R.id.edit_text_profile_last_name).text.toString()
                .isEmpty() -> {
                Toast.makeText(
                    this@UserProfileActivity,
                    "Please enter last name",
                    Toast.LENGTH_SHORT
                )
                    .show()
                false
            }
            findViewById<EditText>(R.id.edit_text_profile_email).text.toString().isEmpty() -> {
                Toast.makeText(this@UserProfileActivity, "Please enter email", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            findViewById<EditText>(R.id.edit_text_profile_phone).text.toString().isEmpty() -> {
                Toast.makeText(this@UserProfileActivity, "Please enter phone", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * Try submitting details
     */
    private fun submitDetails() {
        //Assigning gender variable according to its gender
        val gender: String = if (findViewById<RadioButton>(R.id.btn_male).isChecked) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }

        //If input information given by the user is valid,then upload user details to firestore
        if (validateDetails()) {
            val user = User(
                currentUserId,
                findViewById<EditText>(R.id.edit_text_profile_name).text.toString(),
                findViewById<EditText>(R.id.edit_text_profile_last_name).text.toString(),
                findViewById<EditText>(R.id.edit_text_profile_email).text.toString(),
                "",
                findViewById<EditText>(R.id.edit_text_profile_phone).text.toString(),
                gender,
                1
            )
            Firestore(this@UserProfileActivity).uploadUserDetails(user)
        }
    }
}