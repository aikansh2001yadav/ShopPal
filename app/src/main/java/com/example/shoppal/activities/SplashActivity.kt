package com.example.shoppal.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppal.R
import com.example.shoppal.firebase.Firestore
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (FirebaseAuth.getInstance().currentUser != null) {
            //Delays 2 seconds for getting profile details and start next activity
            Timer().schedule(2000) {
                Firestore(this@SplashActivity).getProfileDetails()
            }
        } else {
            //Delays 2 seconds for starting LoginActivity
            Timer().schedule(2000) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}