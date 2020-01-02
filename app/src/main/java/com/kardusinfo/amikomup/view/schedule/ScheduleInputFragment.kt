package com.kardusinfo.amikomup.view.schedule


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    val user = FirebaseAuth.getInstance().currentUser.toString()
    val namaKelas = tietClassName.text.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNewSchedule(user, namaKelas)
    }

    private fun createNewSchedule(
        uid: String,
        namaKelas: String
    ) {
        val user = hashMapOf(
            "uid" to uid,
            "class_name" to namaKelas
        )

        db.collection("users")
            .document(uid)
            .collection("jadwal")
            .document(namaKelas)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("DATABASE", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("DATABASE", "Error adding document", e)
            }

    }

}
