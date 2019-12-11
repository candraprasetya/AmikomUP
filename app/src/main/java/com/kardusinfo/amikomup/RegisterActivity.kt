package com.kardusinfo.amikomup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        createAnimation()

        buttonRegister.setOnClickListener {
            createNewUser()
        }

        buttonToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        buttonBack.setOnClickListener {
            finish()
        }


    }

    private fun createAnimation() {
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            interpolator = BounceInterpolator()
            duration = 1100L
        }

        layoutInputEmailAddress.animation = fadeIn
        layoutInputPassword.animation = fadeIn
        buttonRegister.animation = fadeIn
    }


    private fun createNewUser() {
        val email = inputEmailAddress.text.toString()
        val passwd = inputPassword.text.toString()

        if (email.isEmpty() || passwd.isEmpty()) {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                val user = auth.currentUser
                Log.d("FIREBASE REGISTER", " $user Account Created")
                finish()
            } else {
                Log.e("FIREBASE REGISTER", it.exception!!.message)
                Toast.makeText(this, "Error to create your account!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
