package com.kardusinfo.amikomup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.BounceInterpolator
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        createAnimation()

        buttonStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun createAnimation() {
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            interpolator = BounceInterpolator()
            duration = 1400L
        }

        logoWelcome.animation = fadeIn
        welcome.animation = fadeIn
        buttonStart.animation = fadeIn
    }

}
