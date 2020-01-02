package com.kardusinfo.amikomup.view.firstrun

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.view.dialogs.LoadingDialog
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var loadingDialog: LoadingDialog
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        loadingDialog = LoadingDialog()

        createAnimation()

        btnReset.setOnClickListener {
            resetMyPasswordPlease(inputResetEmailAddress.text.toString())
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
        kokBisa.startAnimation(topToBottom)
        gapapa.startAnimation(bottomToTop)
        layoutInputResetEmailAddress.startAnimation(bottomToTop)
        findAnimation.startAnimation(scaleToBig)
        btnReset.startAnimation(bottomToTop)
    }

    private fun resetMyPasswordPlease(email: String) {
        if (email.isEmpty()) {
            inputResetEmailAddress.error = "Please fill email address field"
            Toast.makeText(this, "Please fill email address field", Toast.LENGTH_SHORT).show()
        }
        loadingDialog.show(
            supportFragmentManager,
            LoadingDialog.TAG
        )
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                loadingDialog.dismiss()
                Toast.makeText(
                    this,
                    "Sent an E-Mail with further instructions to ${email}",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }.addOnFailureListener {
            loadingDialog.dismiss()
            Toast.makeText(
                this,
                "Couldn't send an E-Mail to ${email} \n ${it.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onBackPressed() {

    }
}
