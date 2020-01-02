package com.kardusinfo.amikomup.view.firstrun

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.view.dashboard.DashboardCandra
import com.kardusinfo.amikomup.view.dialogs.LoadingDialog
import kotlinx.android.synthetic.main.activity_login.*


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

        resetMyPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))

        }


        buttonToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()

        }

        buttonBack.setOnClickListener {
            finish()
        }
    }


    private fun createAnimation() {
        val topToBottom = AnimationUtils.loadAnimation(
            this,
            R.anim.top_to_bottom
        )
        val scaleToBig = AnimationUtils.loadAnimation(
            this,
            R.anim.scale_to_big
        )
        val bottomToTop = AnimationUtils.loadAnimation(
            this,
            R.anim.bottom_to_top
        )

        buttonBack.startAnimation(scaleToBig)
        resetMyPassword.startAnimation(bottomToTop)
        Headline.startAnimation(topToBottom)
        subtitle.startAnimation(topToBottom)
        layoutInputEmailAddress.startAnimation(topToBottom)
        layoutInputPassword.startAnimation(topToBottom)
        buttonToRegister.startAnimation(bottomToTop)
        buttonLogin.startAnimation(bottomToTop)
    }

    private fun loginEmailPassword() {
        val email = inputEmailAddress.text.toString()
        val password = inputPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Fill out All Field", Toast.LENGTH_SHORT).show()
            return
        }


        loadingDialog.show(
            supportFragmentManager,
            LoadingDialog.TAG
        )
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    loadingDialog.dismiss()
                    Toast.makeText(this, "Succesfully Login", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(
                            this,
                            DashboardCandra::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
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
