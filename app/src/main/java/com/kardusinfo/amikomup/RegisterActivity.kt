package com.kardusinfo.amikomup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
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
            startActivity(Intent(this, DashboardCandra::class.java))
            finish()
        }

        buttonBack.setOnClickListener {
            finish()
        }


    }

    private fun createAnimation() {
        val topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)
        val scaleToBig = AnimationUtils.loadAnimation(this, R.anim.scale_to_big)
        val bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)

        buttonBack.startAnimation(scaleToBig)
        HeadlineRegister.startAnimation(topToBottom)
        subtitleRegister.startAnimation(topToBottom)
        layoutInputUsername.startAnimation(topToBottom)
        layoutInputEmailAddress.startAnimation(topToBottom)
        layoutInputPassword.startAnimation(topToBottom)
        buttonToLogin.startAnimation(bottomToTop)
        buttonRegister.startAnimation(bottomToTop)
    }


    private fun createNewUser() {
        val username = inputUsername.text.toString()
        val email = inputEmailAddress.text.toString()
        val password = inputPassword.text.toString()

        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Please Fill out All Field", Toast.LENGTH_SHORT).show()
            return
        }
        loadingDialog.show(supportFragmentManager, LoadingDialog.TAG)
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                loadingDialog.dismiss()
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                val user = auth.currentUser
                if (user != null) {
                    createNewDataUser(user.uid.toString(), username, email)
                }
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

    private fun createNewDataUser(uid: String, username: String, email: String) {
        val user = hashMapOf(
            "username" to username,
            "email" to email
        )

        db.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("DATABASE", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("DATABASE", "Error adding document", e)
            }

    }

    override fun onBackPressed() {

    }
}
