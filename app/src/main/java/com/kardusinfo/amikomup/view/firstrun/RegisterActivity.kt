package com.kardusinfo.amikomup.view.firstrun

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.view.dashboard.DashboardCandra
import com.kardusinfo.amikomup.view.dialogs.LoadingDialog
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var loadingDialog: LoadingDialog
    private val nimWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (!checkNimPattern(s.toString())) {
                inputNim.error = "Format NIM belum tepat!"
            }
        }
    }
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
        HeadlineRegister.startAnimation(topToBottom)
        subtitleRegister.startAnimation(topToBottom)
        layoutInputUsername.startAnimation(topToBottom)
        layoutInputEmailAddress.startAnimation(topToBottom)
        layoutInputPassword.startAnimation(topToBottom)
        buttonToLogin.startAnimation(bottomToTop)
        buttonRegister.startAnimation(bottomToTop)
        layoutInputNim.startAnimation(topToBottom)
    }


    private fun createNewUser() {
        val username = inputUsername.text.toString()
        val email = inputEmailAddress.text.toString()
        val password = inputPassword.text.toString()
        val nim = inputNim.text.toString()

        val nims = nim.split(".")

        val fotoUrl =
            "http://www.amikom.ac.id/public/fotomhs/20${nims[0]}/${nims[0]}_${nims[1]}_${nims[2]}.jpg"

        Log.d("POTOKU",fotoUrl)
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || nim.isEmpty()) {
            Toast.makeText(this, "Please Fill out All Field", Toast.LENGTH_SHORT).show()
            return
        }
        if (!checkNimPattern(nim)) {
            Toast.makeText(this, "Cek Nim yang anda inputkan", Toast.LENGTH_SHORT).show()
            return
        }
        loadingDialog.show(
            supportFragmentManager,
            LoadingDialog.TAG
        )
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                loadingDialog.dismiss()
                startActivity(
                    Intent(
                        this,
                        DashboardCandra::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                val user = auth.currentUser
                if (user != null) {
                    createNewDataUser(user.uid, username, email, nim, fotoUrl, password)
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

    private fun createNewDataUser(
        uid: String,
        username: String,
        email: String,
        nim: String,
        foto: String,
        password: String
    ) {
        val user = hashMapOf(
            "username" to username,
            "email" to email,
            "nim" to nim,
            "profileImage" to foto,
            "password" to password
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

    private fun checkNimPattern(nim: String): Boolean {
        val regex = "[0-9]{2}[.][0-9]{2}[.][0-9]{4}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(nim)

        return matcher.matches()
    }
    override fun onBackPressed() {

    }
}
