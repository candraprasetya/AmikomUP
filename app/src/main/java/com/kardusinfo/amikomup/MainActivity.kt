package com.kardusinfo.amikomup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonBimbingan.setOnClickListener {
            startActivity(Intent(this, BimbinganActivity::class.java))
            getUserData()
            finish()
        }

        buttonSignOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun getUserData() {
        val emailOfUser = auth.currentUser!!.email
        userEmail.text = emailOfUser
    }

    override fun onBackPressed() {
        finish()
    }
}