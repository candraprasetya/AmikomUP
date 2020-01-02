package com.kardusinfo.amikomup.view.schedule


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
import kotlinx.android.synthetic.main.fragment_schedule_input.*

/**
 * A simple [Fragment] subclass.
 */
class ScheduleInputFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser!!.uid


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mbtnAddSchedule.setOnClickListener {
            val namaKelas = tietClassName.text.toString()
            val namaDosen = tietLecturer.text.toString()
            val hari = tietDay.text.toString()
            val ruangKelas = tietRoom.text.toString()
            val jam = tietTime.text.toString()
            createNewSchedule(user, namaKelas, namaDosen, hari, ruangKelas, jam)
        }
    }

    private fun createNewSchedule(
        uid: String,
        namaKelas: String,
        namaDosen: String,
        hari: String,
        ruangKelas: String,
        jam: String
    ) {
        val jadwal = hashMapOf(
            "uid" to uid,
            "class_name" to namaKelas,
            "lecturer_name" to namaDosen,
            "day" to hari,
            "class_room" to ruangKelas,
            "time" to jam
        )

        db.collection("users")
            .document(uid)
            .collection("jadwal")
            .document()
            .set(jadwal)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context, "Sukses menambahkan jadwal", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Gagal menambahkan jadwal\n${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

}
