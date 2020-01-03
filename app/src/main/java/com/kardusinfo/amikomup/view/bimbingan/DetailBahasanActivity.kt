package com.kardusinfo.amikomup.view.bimbingan

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kardusinfo.amikomup.R
import kotlinx.android.synthetic.main.activity_acrtivity_detail_bahasan.*


class DetailBahasanActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acrtivity_detail_bahasan)

        //terimaintentnya
        // tampilkan datanya

        val userId = auth.currentUser!!.uid
        getUserData(userId)

    }

    private fun getUserData(uid: String) {
        db.collection("bimbingan")
            .document(uid)
            .get()
            .addOnSuccessListener { result ->
                txtDate.text = result.getString("tanggal")
                txtBahasan.text = result.getString("topik")
                txtDosen.text = result.getString("waktu")
                val fileURl = result.getString("link")

            }.addOnFailureListener { exception ->
                Log.w("DATABASE GET", "Error getting documents.", exception)
            }
    }
}

