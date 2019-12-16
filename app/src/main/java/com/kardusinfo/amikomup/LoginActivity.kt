package com.kardusinfo.amikomup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import android.view.WindowManager
import android.view.animation.BounceInterpolator
import android.widget.Toast


class LoginActivity : AppCompatActivity() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        createAnimation()
        loadingDialog = LoadingDialog()
        buttonLogin.setOnClickListener {
            loginEmailPassword()
        }


        buttonToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
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

        layoutInputPassword.animation = fadeIn
        layoutInputPassword.animation = fadeIn
        buttonLogin.animation = fadeIn
    }

    private fun loginEmailPassword() {
        val email = inputEmailAddress.text.toString()
        val password = inputPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            return
        }
        loadingDialog.show(supportFragmentManager, LoadingDialog.TAG)
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    loadingDialog.dismiss()
                    Toast.makeText(this, "Succesfully Login", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }.addOnFailureListener {
                loadingDialog.dismiss()
                Log.d("FIREBASE", "Failed Login : ${it.message}")
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onBackPressed() {

    }

}
