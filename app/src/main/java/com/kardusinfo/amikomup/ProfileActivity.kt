package com.kardusinfo.amikomup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    lateinit var iconuser : EditText
    lateinit var nim : EditText
    lateinit var userImage : ImageView
    lateinit var update : Button
    lateinit var database : DatabaseReference
    lateinit var Auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        iconuser = findViewById(R.id.iconUser)
        userImage = findViewById(R.id.profile_image)

        Auth = FirebaseAuth.getInstance()
        val id  = Auth.currentUser.id

        Database = FireDatabase.getIntance().getReference("user")

    }
}
