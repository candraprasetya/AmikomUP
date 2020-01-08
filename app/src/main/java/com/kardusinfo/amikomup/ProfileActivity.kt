package com.kardusinfo.amikomup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.kardusinfo.amikomup.view.activities.WelcomeActivity
import com.kardusinfo.amikomup.view.dashboard.DashboardCandra
import kotlinx.android.synthetic.main.activity_dashboard_candra.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logout.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    WelcomeActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )

        }

            akun.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        EditUser::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )


            }
        }
    }

