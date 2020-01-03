package com.kardusinfo.amikomup.view.bimbingan.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.view.dialogs.LoadingDialog
import com.takisoft.datetimepicker.DatePickerDialog
import com.takisoft.datetimepicker.TimePickerDialog
import kotlinx.android.synthetic.main.activity_bimbingan.*
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
    lateinit var storageReference: StorageReference
    private var linkKU = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_bimbingan_book, container, false)



        tabs_main.setupWithViewPager(viewPagerBimbingan)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, file: Intent?) {
        super.onActivityResult(requestCode, resultCode, file)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            val uploadTask = storageReference.putFile(file!!.data!!)
            val task = uploadTask.continueWithTask {

                    task ->
                if (!task.isSuccessful) {
                    Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
                }
                storageReference.downloadUrl
            }.addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val url = downloadUri!!.toString()

                    Log.d("DIRECT", url)
                    linkKU = url
                }
            }

            val SelectedFile = file.data
            namaFile.text = SelectedFile.toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storageReference = FirebaseStorage.getInstance().getReference("File_Upload")
        loadingDialog = LoadingDialog()

        val userId = auth.currentUser!!.uid
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)




        btnUpload.setOnClickListener {
            val intent = Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Pilih File"), 111)
        }


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
                    "Pending",
                    linkKU
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
        status: String,
        link: String
    ) {
        val user = hashMapOf(
            "userId" to uid,
            "matakuliah" to matkul,
            "tanggal" to tanggal,
            "waktu" to waktu,
            "topik" to topik,
            "status" to status,
            "link" to link
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
