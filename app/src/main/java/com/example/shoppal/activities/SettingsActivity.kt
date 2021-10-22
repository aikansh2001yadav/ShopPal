package com.example.shoppal.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shoppal.R
import com.example.shoppal.firebase.Firebase
import com.example.shoppal.interfaces.UserProfileDetailsInterface
import com.example.shoppal.utils.Constants

class SettingsActivity : AppCompatActivity(), View.OnClickListener, UserProfileDetailsInterface {
    /**
     * Shows reference of progress bar that shows progress
     */
    private var settingsProgress: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingsProgress = findViewById(R.id.settings_progress)
        //Sets on click listener on below views
        findViewById<TextView>(R.id.btn_edit_settings).setOnClickListener(this)
        findViewById<View>(R.id.btn_settings_logout).setOnClickListener(this)
    }

    /**
     * Sets user details of the current user
     */
    override fun setUserDetails() {
        //Shows progress bar
        settingsProgress!!.visibility = View.VISIBLE
        val sharedPreferences = this.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        val imageUrl = sharedPreferences.getString(Constants.PROFILE_IMAGE, null)
        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).centerCrop()
                .into(findViewById(R.id.profile_settings_imageview))
        }
        val name = sharedPreferences.getString(Constants.NAME, "")
        val lastName = sharedPreferences.getString(Constants.LAST_NAME, "")
        findViewById<EditText>(R.id.text_profile_settings_name).setText("$name $lastName")
        findViewById<EditText>(R.id.text_profile_settings_email).setText(
            sharedPreferences.getString(
                Constants.EMAIL,
                ""
            )
        )
        findViewById<EditText>(R.id.text_profile_settings_number).setText(
            sharedPreferences.getString(
                Constants.MOBILE_NUMBER,
                ""
            )
        )
        //Hides progress bar
        settingsProgress!!.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_edit_settings -> {
                    //Starts UserProfileActivity
                    val intent = Intent(this@SettingsActivity, UserProfileActivity::class.java)
                    intent.putExtra(Constants.TRANSITION_FROM_SETTINGS, true)
                    startActivity(intent)
                }
                R.id.btn_settings_logout -> {
                    //Logout current user
                    Firebase(this).logoutUser()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //Sets user details of the current user
        setUserDetails()
    }
}