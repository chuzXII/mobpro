package com.nixie.projectku

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val logo = findViewById<ImageView>(R.id.logo)

        // Memuat animasi
        val moveUpAnimation = AnimationUtils.loadAnimation(this, R.anim.move_up)
        moveUpAnimation.fillAfter = true
        // Menjalankan animasi
        logo.startAnimation(moveUpAnimation)

        moveUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@SplashActivity, logo, "logoTransition")
                startActivity(intent, options.toBundle())
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}