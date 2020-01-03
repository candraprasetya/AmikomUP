package com.kardusinfo.amikomup.view.dashboard

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.view.activities.WelcomeActivity
import com.kardusinfo.amikomup.view.bimbingan.BimbinganActivity
import com.kardusinfo.amikomup.view.schedule.ScheduleActivity
import kotlinx.android.synthetic.main.activity_dashboard_candra.*
import kotlinx.android.synthetic.main.sign_out_dialog.view.*


class DashboardCandra : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_candra)

        createAnimation()

        val userId = auth.currentUser!!.uid

        getUserData(userId)


        profileUser.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.sign_out_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("SIGN OUT")
                .setCancelable(false)
            val mAlertDialog = mBuilder.show()
            mDialogView.buttonYES.setOnClickListener {
                mAlertDialog.dismiss()
                auth.signOut()
                startActivity(
                    Intent(
                        this,
                        WelcomeActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                finish()
            }
            mDialogView.buttonNO.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }


        buttonToBimbingan.setOnClickListener {
            startActivity(Intent(this, BimbinganActivity::class.java))
        }


        buttonToCekJadwal.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
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
        val btnGreen = AnimationUtils.loadAnimation(
            this,
            R.anim.button_green
        )
        val btnRed = AnimationUtils.loadAnimation(
            this,
            R.anim.button_red
        )

        val btnYellow = AnimationUtils.loadAnimation(
            this,
            R.anim.button_yellow
        )


        profileUser.startAnimation(scaleToBig)
        placeholder.startAnimation(scaleToBig)
        usernameText.startAnimation(bottomToTop)
        nimText.startAnimation(topToBottom)
        buttonToBimbingan.startAnimation(btnGreen)
        buttonToCekJadwal.startAnimation(btnRed)
        buttonToKalender.startAnimation(btnYellow)
    }

    private fun getUserData(uid: String) {

        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { result ->
                welcomeText.text = "Selamat Datang Kembali, "
                usernameText.text = result.getString("username")
                val imageURL = result.getString("profileImage")
                nimText.text = result.getString("nim")

                Glide.with(this)
                    .load(imageURL)
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Toast.makeText(
                                this@DashboardCandra,
                                "Fotomu Gak Ada",
                                Toast.LENGTH_SHORT
                            ).show()
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            placeholder.visibility = View.GONE
                            return false
                        }
                    })
                    .centerCrop()
                    .apply(
                        RequestOptions(

                        )
                    )
                    .into(profileUser)
            }
            .addOnFailureListener { exception ->
                Log.w("DATABASE GET", "Error getting documents.", exception)
            }

    }

}
