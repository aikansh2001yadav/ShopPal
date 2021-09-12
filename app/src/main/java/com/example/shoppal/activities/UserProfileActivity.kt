package com.example.shoppal.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shoppal.R
import com.example.shoppal.firebase.FirebaseStorage
import com.example.shoppal.firebase.Firestore
import com.example.shoppal.models.User
import com.example.shoppal.utils.Constants

class UserProfileActivity : AppCompatActivity(), View.OnClickListener {
    /**
     * Stores image url stored firebase storage
     */
    private var profileImage = ""

    /**
     * Stores uri of image chosen as profile image
     */
    private var uri: Uri? = null

    /**
     * Stores reference of ActivityResultLaunches that allows user to choose image for profile image view
     */
    private lateinit var content: ActivityResultLauncher<String>

    /**
     * Stores id of current user
     */
    private var currentUserId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        content = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            //Getting profile image and setting uri into profile image view with glide
            Glide.with(this).load(uri).into(findViewById(R.id.profile_imageview))
            this.uri = uri
        }

        //Sets user profile details
        setUserProfileDetails()

        //Setting on click listener on below views
        findViewById<ImageView>(R.id.profile_imageview).setOnClickListener(this)
        findViewById<View>(R.id.btn_profile_submit).setOnClickListener(this)
    }

    /**
     * Sets user profile details from the sharedPreferences
     */
    private fun setUserProfileDetails() {
        val sharedPreferences = this.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        //Stores image url of the user from shared preferences
        val imageUrl = sharedPreferences.getString(Constants.PROFILE_IMAGE, null)
        //If image url is not null, then set profile image of the user using glide
        if (imageUrl != null) {
            Glide.with(this).load(sharedPreferences.getString(Constants.PROFILE_IMAGE, null))
                .into(findViewById(R.id.profile_imageview))
            profileImage = imageUrl
        }
        //Stores current user id
        currentUserId = sharedPreferences.getString(Constants.ID, "")!!
        //Sets profile name of the user
        findViewById<EditText>(R.id.edit_text_profile_name).setText(
            sharedPreferences.getString(
                Constants.NAME,
                ""
            )
        )
        //Sets profile last name of the user
        findViewById<EditText>(R.id.edit_text_profile_last_name).setText(
            sharedPreferences.getString(
                Constants.LAST_NAME,
                ""
            )
        )
        //Sets profile email of the user
        findViewById<EditText>(R.id.edit_text_profile_email).setText(
            sharedPreferences.getString(
                Constants.EMAIL,
                ""
            )
        )
        //Sets profile phone number of the user
        findViewById<EditText>(R.id.edit_text_profile_phone).setText(
            sharedPreferences.getString(
                Constants.MOBILE_NUMBER,
                ""
            )
        )
        //Stores gender of the user
        val gender = sharedPreferences.getString(Constants.GENDER, "")
        //Checks male or female depending on the gender of the user
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
                profileImage,
                findViewById<EditText>(R.id.edit_text_profile_phone).text.toString(),
                gender,
                1
            )
            if (uri != null) {
                Firestore(this@UserProfileActivity).uploadUserDetails(user, false)
                FirebaseStorage(currentUserId, this@UserProfileActivity).uploadImage(
                    uri!!,
                    profileImage
                )
            } else {
                Firestore(this@UserProfileActivity).uploadUserDetails(user, true)
            }
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_profile_submit -> {
                    submitDetails()
                }
                R.id.profile_imageview -> {
                    content.launch("image/*")
                }
            }
        }
    }
}