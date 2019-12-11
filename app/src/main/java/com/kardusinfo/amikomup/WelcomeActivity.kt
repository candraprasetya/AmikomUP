package com.kardusinfo.amikomup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        checkUser()

        setContentView(R.layout.activity_first)


        buttonStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun checkUser() {
        if(mAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
