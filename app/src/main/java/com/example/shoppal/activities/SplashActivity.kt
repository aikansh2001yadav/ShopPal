package com.example.shoppal.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppal.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(FirebaseAuth.getInstance().currentUser != null){
            //Delays 2 seconds for starting LoginActivity
            Timer().schedule(2000) {
                startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                finish()
            }
        }else{
            //Delays 2 seconds for starting LoginActivity
            Timer().schedule(2000) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
        }
    }
}}