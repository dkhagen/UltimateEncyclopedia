package com.example.ultimateencyclopedia.home

import android.graphics.drawable.AnimationDrawable
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

}