package com.kardusinfo.amikomup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_schedule.*

class ScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        ibAdd.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ScheduleInputActivity::class.java
                )
            )
        }
    }

}