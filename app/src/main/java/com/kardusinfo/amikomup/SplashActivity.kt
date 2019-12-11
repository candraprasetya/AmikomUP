package com.kardusinfo.amikomup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mAuth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            checkUser()

            // close this activity
            finish()
        }, 3000)



    }
    private fun checkUser() {
        if(mAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }
}
