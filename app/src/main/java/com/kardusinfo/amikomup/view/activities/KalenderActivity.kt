package com.kardusinfo.amikomup.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.adapter.KalenderAdapter
import kotlinx.android.synthetic.main.activity_kalender.*

class KalenderActivity : AppCompatActivity() {

    var images: Array<Int> = arrayOf(
        R.drawable.ic_september_2019,
        R.drawable.ic_october_2019,
        R.drawable.ic_november_2019,
        R.drawable.ic_december_2019,
        R.drawable.ic_january_2020,
        R.drawable.ic_february_2020,
        R.drawable.ic_march_2020,
        R.drawable.ic_april_2020,
        R.drawable.ic_may_2020,
        R.drawable.ic_june_2020,
        R.drawable.ic_july_2020,
        R.drawable.ic_august_2020
    )

    var adapter: PagerAdapter = KalenderAdapter(this, images)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalender)

        viewKalender.adapter = adapter
        backFromKalender.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
