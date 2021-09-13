package com.example.shoppal.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.shoppal.activities.DashboardActivity
import com.example.shoppal.activities.UserProfileActivity
import com.example.shoppal.models.User
import com.example.shoppal.utils.Constants
import com.example.shoppal.utils.Tags
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
     * Uploads user details on firebase and starts UserProfileActivity if profile is not completed
     */
    fun uploadUserDetails(user: User) {
        db.collection(Constants.USERS)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d(Tags.PROFILE_DETAILS_UPLOAD, "DocumentSnapshot successfully written!")
                //Saving user details to sharedPreferences
                saveUserProfileDetails(user)
                if (user.profileCompleted == 0) {
                    baseActivity.startActivity(
                        Intent(
                            baseActivity,
                            UserProfileActivity::class.java
                        )
                    )
                    baseActivity.finish()
                }
            }
    }

    /**
     * Uploads user details on firebase and start Dashboard Activity if profile is not completed else start UserProfileActivity
     */
    fun uploadUserDetailsDashboardActivity(user: User) {
        db.collection(Constants.USERS)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d(Tags.PROFILE_DETAILS_UPLOAD, "DocumentSnapshot successfully written!")
                //Saving user details to sharedPreferences
                saveUserProfileDetails(user)
                if (user.profileCompleted == 0) {
                    baseActivity.startActivity(
                        Intent(
                            baseActivity,
                            UserProfileActivity::class.java
                        )
                    )
                } else {
                    val intent = Intent(baseActivity, DashboardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    baseActivity.startActivity(intent)
                }
                baseActivity.finish()
            }
    }

    /**
     * Uploads user details on firebase and start Settings Activity if profile is not completed else start UserProfileActivity
     */
    fun uploadUserDetailsSettingsActivity(user: User) {
        db.collection(Constants.USERS)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d(Tags.PROFILE_DETAILS_UPLOAD, "DocumentSnapshot successfully written!")
                //Saving user details to sharedPreferences
                saveUserProfileDetails(user)
                if (user.profileCompleted == 0) {
                    baseActivity.startActivity(
                        Intent(
                            baseActivity,
                            UserProfileActivity::class.java
                        )
                    )
                }
                baseActivity.finish()
            }
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
                    if (document.data != null) {
                        //Creating an instance of User from document reference
                        val user: User = document.toObject(User::class.java)!!
                        //Saving user profile details to sharedPreferences
                        saveUserProfileDetails(user)
                        //If user's profile is completed, start UserProfileActivity otherwise DashboardActivity
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
                                    DashboardActivity::class.java
                                )
                            )
                        }
                        baseActivity.finish()
                        Log.d(Tags.GET_PROFILE_DETAILS, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(Tags.GET_PROFILE_DETAILS, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(Tags.GET_PROFILE_DETAILS, "get failed with ", exception)
                }
        }
    }
}