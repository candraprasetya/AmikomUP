package com.kardusinfo.amikomup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        loadingDialog = LoadingDialog()

        createAnimation()

        buttonRegister.setOnClickListener {
            createNewUser()
        }

        buttonToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        buttonBack.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }


    }

    private fun createAnimation() {
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            interpolator = BounceInterpolator()
            duration = 1400L
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

        loadingDialog.show(supportFragmentManager, LoadingDialog.TAG)
        auth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener {
            if (it.isSuccessful) {
                loadingDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                val user = auth.currentUser
                Log.d("FIREBASE REGISTER", " $user Account Created")
                finish()
            }
        }
            .addOnFailureListener {
                loadingDialog.dismiss()
                Log.e("FIREBASE REGISTER", "Failed Login : ${it.message}")
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }

    }

    override fun onBackPressed() {

    }
}
