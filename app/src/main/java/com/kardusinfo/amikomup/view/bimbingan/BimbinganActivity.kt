package com.kardusinfo.amikomup.view.bimbingan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.activity_bimbingan.*
import kotlinx.android.synthetic.main.fragment_bimbingan_book.*

class BimbinganActivity : AppCompatActivity() {


    //Deklarasi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bimbingan)

        viewPagerBimbingan.adapter = FragmentAdapter(supportFragmentManager)


        tabs_main.setupWithViewPager(viewPagerBimbingan)


    }



}

