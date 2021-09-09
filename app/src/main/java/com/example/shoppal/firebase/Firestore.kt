package com.example.shoppal.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.shoppal.MainActivity
import com.example.shoppal.activities.UserProfileActivity
import com.example.shoppal.models.User
import com.example.shoppal.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Firestore(private val baseActivity: Activity) {
    /**
     * Stores an instance of firestore
     */
    private val db = Firebase.firestore

    /**
     * Uploads user profile details to firestore, saves these details to sharedPreferences and start activity of UserProfileActivity
     */
    fun uploadUserDetails(user: User) {
        db.collection(Constants.USERS)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("UploadSuccess", "DocumentSnapshot successfully written!")
                //Saving user details to sharedPreferences
                saveUserProfileDetails(user)
                if (user.profileCompleted == 0) {
                    //Starting UserProfileActivity
                    baseActivity.startActivity(
                        Intent(
                            baseActivity,
                            UserProfileActivity::class.java
                        )
                    )
                    //Finishing base activity
                    baseActivity.finish()
                } else {
                    baseActivity.startActivity(Intent(baseActivity, MainActivity::class.java))
                    baseActivity.finish()
                }
            }
            .addOnFailureListener { e -> Log.w("UploadFailure", "Error writing document", e) }
    }


    /**
     * Saves user profile details to sharedPreferences,
     */
    private fun saveUserProfileDetails(user: User) {
        val sharedPreferences =
            baseActivity.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.ID, user.id)
        editor.putString(Constants.NAME, user.name)
        editor.putString(Constants.LAST_NAME, user.lastName)
        editor.putString(Constants.EMAIL, user.email)
        editor.putString(Constants.PROFILE_IMAGE, user.profileImage)
        editor.putString(Constants.MOBILE_NUMBER, user.mobileNumber)
        editor.putString(Constants.GENDER, user.gender)
        editor.apply()
    }

    /**
     * Gets profile details from firestore, saves these details to sharedPreferences and start activity on the basis of profile completion
     */
    fun getProfileDetails() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            db.collection(Constants.USERS)
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        //Creating an instance of User from document reference
                        val user: User = document.toObject(User::class.java)!!
                        //Saving user profile details to sharedPreferences
                        saveUserProfileDetails(user)
                        //If user's profile is completed, start UserProfileActivity otherwise MainActivity
                        if (user.profileCompleted == 0) {
                            baseActivity.startActivity(
                                Intent(
                                    baseActivity,
                                    UserProfileActivity::class.java
                                )
                            )
                        } else {
                            baseActivity.startActivity(
                                Intent(
                                    baseActivity,
                                    MainActivity::class.java
                                )
                            )
                        }
                        baseActivity.finish()
                        Log.d("GET_DETAILS_SUCCESS", "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d("DETAILS_NOT_FOUND", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("GET_DETAILS_FAILURE", "get failed with ", exception)
                }
        }
    }
}