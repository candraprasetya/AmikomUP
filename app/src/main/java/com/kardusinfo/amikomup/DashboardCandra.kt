package com.kardusinfo.amikomup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dashboard_candra.*


class DashboardCandra : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_candra)

        val userId = auth.currentUser!!.uid
        getUserData(userId)

        btnSchedule.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ScheduleActivity::class.java
                )
            )
        }

        buttonSignOut.setOnClickListener {
            auth.signOut()
            startActivity(
                Intent(
                    this,
                    WelcomeActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }

    private fun getUserData(uid: String) {

        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { result ->
                usernameText.text = result.getString("username")

            }
            .addOnFailureListener { exception ->
                Log.w("DATABASE GET", "Error getting documents.", exception)
            }

    }
}
