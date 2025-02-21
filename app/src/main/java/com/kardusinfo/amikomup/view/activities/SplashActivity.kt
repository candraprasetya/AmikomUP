package com.kardusinfo.amikomup.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.view.dashboard.DashboardCandra

class SplashActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        checkUser()
    }


    private fun checkUser() {
        if (mAuth.currentUser != null) {
            startActivity(Intent(this, DashboardCandra::class.java))
            finish()
        } else {
            setContentView(R.layout.activity_splash)
            Handler().postDelayed({
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }, 3000)
        }
    }
}

