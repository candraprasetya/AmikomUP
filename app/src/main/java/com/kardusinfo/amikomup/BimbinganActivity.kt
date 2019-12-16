package com.kardusinfo.amikomup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class BimbinganActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bimbingan)

        buttonToDashboard.setOnClickListener {
            startActivity(Intent(this,DashboardActivity::class.java))
        getUserData()
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
}