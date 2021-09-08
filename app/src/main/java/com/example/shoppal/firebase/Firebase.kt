package com.example.shoppal.firebase

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.shoppal.MainActivity
import com.google.firebase.auth.FirebaseAuth

class Firebase(private val baseActivity: Activity) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    firebaseAuth.signOut()
                    startMainActivity()
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

    private fun startMainActivity() {
        val intent = Intent(baseActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        baseActivity.applicationContext.startActivity(intent)
    }


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