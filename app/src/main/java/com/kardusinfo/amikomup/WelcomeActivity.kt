package com.kardusinfo.amikomup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_welcome)


        buttonStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


}
