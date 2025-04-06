package com.example.speerinterviewsubmission.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.speerinterviewsubmission.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_scree)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        animation()
    }


    private fun animation() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val logo = findViewById<ConstraintLayout>(R.id.container_logo)
                logo.animate().setDuration(800).alphaBy(1f).alpha(0f)
                    .setListener(object: Animator.AnimatorListener{
                        override fun onAnimationStart(p0: Animator) {
                        }

                        override fun onAnimationEnd(p0: Animator) {
                            val intent = Intent(this@SplashScreen,MainActivity::class.java)
                            this@SplashScreen.startActivity(intent)
                            this@SplashScreen.finish()
                        }

                        override fun onAnimationCancel(p0: Animator) {
                        }

                        override fun onAnimationRepeat(p0: Animator) {
                        }
                    })
            },
            500
        )
    }

    override fun finish() {
        super.finish()
    }
}