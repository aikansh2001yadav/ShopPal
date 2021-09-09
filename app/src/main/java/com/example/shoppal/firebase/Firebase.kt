package com.example.shoppal.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Firebase(private val baseActivity: Activity) {
    /**
     * Stores an instance of FirebaseAuth
     */
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Login user to firebase and get profile details from firestore after logging in
     */
    fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    //Getting profile details from firestore
                    Firestore(baseActivity).getProfileDetails()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    /**
     * Sends password reset email to the given email address
     */
    fun sendPasswordResetEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseActivity,
                        "Please check your email to reset",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}