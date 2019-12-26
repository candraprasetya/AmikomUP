package com.kardusinfo.amikomup.view.bimbingan.fragments


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.view.dialogs.LoadingDialog
import kotlinx.android.synthetic.main.fragment_bimbingan_book.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class BimbinganBook : Fragment() {

    private val databaseBimbingan = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var loadingDialog: LoadingDialog
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bimbingan_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog()

        val userId = auth.currentUser!!.uid
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)

        layoutEdtCalendar.setEndIconOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.TimePickerTheme,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    edtCalendar.setText(
                        dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                    )
                },
                mYear,
                mMonth,
                mDay
            )
            datePickerDialog.setCancelable(false)
            datePickerDialog.setIcon(R.drawable.jadwal)
            datePickerDialog.show()
        }

        layoutEdtTime.setEndIconOnClickListener {
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                R.style.TimePickerTheme,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> edtTime.setText("$hourOfDay:$minute") },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.setCancelable(false)
            timePickerDialog.setIcon(R.drawable.clock)
            timePickerDialog.show()
        }


        btnBooking.setOnClickListener {
            if (edtMatkul.text!!.isEmpty() || edtCalendar.text!!.isEmpty() || edtTime.text!!.isEmpty() || edtDetail.text!!.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Mohon Lengkapi Data Pengajuan Bimbingan Anda!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                addBimbingan(
                    userId,
                    edtMatkul.text.toString(),
                    edtCalendar.text.toString(),
                    edtTime.text.toString(),
                    edtDetail.text.toString(),
                    "Pending"
                )
            }
        }
    }

    private fun addBimbingan(
        uid: String,
        matkul: String,
        tanggal: String,
        waktu: String,
        topik: String,
        status: String
    ) {
        val user = hashMapOf(
            "userId" to uid,
            "matakuliah" to matkul,
            "tanggal" to tanggal,
            "waktu" to waktu,
            "topik" to topik,
            "status" to status
        )
        loadingDialog.show(
            requireFragmentManager(),
            LoadingDialog.TAG
        )

        databaseBimbingan.collection("users")
            .document(uid)
            .collection("bimbingan")
            .document()
            .set(user)
            .addOnSuccessListener { documentReference ->
                loadingDialog.dismiss()
                Toast.makeText(requireContext(), "Bimbingan Telah Diajukan", Toast.LENGTH_SHORT)
                    .show()
                Log.d("DATABASE", "DocumentSnapshot added with ID: $documentReference")
                edtMatkul.setText("")
                edtCalendar.setText("")
                edtTime.setText("")
                edtDetail.setText("")
            }
            .addOnFailureListener { e ->
                loadingDialog.dismiss()
                Toast.makeText(requireContext(), "Bimbingan Gagal Diajukan!", Toast.LENGTH_SHORT)
                    .show()
                Log.w("DATABASE", "Error adding document", e)
            }

    }


}
