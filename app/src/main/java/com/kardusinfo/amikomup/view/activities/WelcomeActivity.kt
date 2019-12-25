package com.kardusinfo.amikomup.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.view.firstrun.LoginActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        createAnimation()

        buttonStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
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

        welcome.startAnimation(topToBottom)
        logoWelcome.startAnimation(scaleToBig)
        buttonStart.startAnimation(bottomToTop)
        subtitleWelcome.startAnimation(topToBottom)
    }

    override fun onBackPressed() {
        finish()
    }

}
