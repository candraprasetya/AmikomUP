package com.kardusinfo.amikomup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_bimbingan.*

class BimbinganActivity : AppCompatActivity() {


    //Deklarasi
    private val databaseBimbingan = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bimbingan)

        btnBookingState.setOnClickListener {
        }
    }

    private fun addBimbingan() {
        val dataBimbingan = hashMapOf(
            "nama_dosen" to edtDosen.text
        )
    }

}