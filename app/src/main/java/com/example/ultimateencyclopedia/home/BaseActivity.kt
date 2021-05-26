package com.example.ultimateencyclopedia.home

import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ultimateencyclopedia.R
import com.example.ultimateencyclopedia.database.UltimateDatabase

open class BaseActivity : AppCompatActivity() {

    lateinit var ultimateDatabase: UltimateDatabase

    fun startBackgroundAnimation() {
        val animatedDrawable =
            findViewById<ConstraintLayout>(R.id.cl_home).background as AnimationDrawable
        animatedDrawable.setEnterFadeDuration(10)
        animatedDrawable.setExitFadeDuration(5000)
        animatedDrawable.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
    }

    fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

}