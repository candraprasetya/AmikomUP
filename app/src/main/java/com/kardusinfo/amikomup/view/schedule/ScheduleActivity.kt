package com.kardusinfo.amikomup.view.schedule

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.utils.replaceFragment
import kotlinx.android.synthetic.main.activity_schedule.*

class ScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        ibAdd.setOnClickListener {
            replaceFragment(ScheduleInputFragment(), R.id.frameSchedule)
            ibAdd.visibility = View.GONE
            tvTitle.text = "Add New Schedule"
        }
        backFromSchedule.setOnClickListener {
            if (tvTitle.text == "My Schedule") {
                finish()
            } else {
                replaceFragment(ScheduleListFragment(), R.id.frameSchedule)
                tvTitle.text = "My Schedule"
                ibAdd.visibility = View.VISIBLE
            }
        }

    }
}
