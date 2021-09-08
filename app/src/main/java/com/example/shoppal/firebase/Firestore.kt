package com.example.shoppal.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.shoppal.MainActivity
import com.example.shoppal.activities.UserProfileActivity
import com.example.shoppal.models.User
import com.example.shoppal.utils.Constants
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Firestore(private val baseActivity: Activity) {
    private val db = Firebase.firestore

    fun uploadUserDetails(user: User) {
        db.collection(Constants.USERS)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("UploadSuccess", "DocumentSnapshot successfully written!")
                saveUserProfileDetails(user)
                baseActivity.startActivity(
                    Intent(
                        baseActivity,
                        UserProfileActivity::class.java
                    )
                )
                baseActivity.finish()
            }
            .addOnFailureListener { e -> Log.w("UploadFailure", "Error writing document", e) }
    }


    private fun saveUserProfileDetails(user: User) {
        val sharedPreferences =
            baseActivity.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.NAME, user.name)
        editor.putString(Constants.LAST_NAME, user.lastName)
        editor.putString(Constants.EMAIL, user.email)
        editor.putString(Constants.PROFILE_IMAGE, user.profileImage)
        editor.putString(Constants.MOBILE_NUMBER, user.mobileNumber)
        editor.putString(Constants.GENDER, user.gender)
        editor.apply()
    }
}