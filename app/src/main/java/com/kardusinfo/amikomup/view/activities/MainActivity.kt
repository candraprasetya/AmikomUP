package com.kardusinfo.amikomup.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kardusinfo.amikomup.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onBackPressed() {
        finish()
    }
}