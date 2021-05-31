package com.example.ultimateencyclopedia.home

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ultimateencyclopedia.R
import com.example.ultimateencyclopedia.utils.Utils

open class BaseActivity : AppCompatActivity() {

    lateinit var clHome: ConstraintLayout

    fun startBackgroundAnimation() {
        val animatedDrawable =
            findViewById<ConstraintLayout>(R.id.cl_home).background as AnimationDrawable
        animatedDrawable.setEnterFadeDuration(10)
        animatedDrawable.setExitFadeDuration(5000)
        animatedDrawable.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
    }

    private fun bindViews() {
        clHome = findViewById(R.id.cl_home)
    }

    @Suppress("DEPRECATION")
    fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//             window.insetsController?.hide(WindowInsets.Type.statusBars())
            window.setDecorFitsSystemWindows(false)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        window.statusBarColor = Color.TRANSPARENT
        adjustBottomPadding()
    }

    @Suppress("DEPRECATION")
    private fun adjustBottomPadding() {
        val resources = applicationContext.resources
        val resourceId = resources.getIdentifier(NAVIGATION_BAR_HEIGHT, DIMEN, ANDROID)
        val bottomNavBarHeight = resources.getDimensionPixelSize(resourceId)

        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.display?.getRealMetrics(displayMetrics)
        } else {

            val display = this.windowManager.defaultDisplay
            display.getMetrics(displayMetrics)
        }
        val bottomPadding =
            (displayMetrics.heightPixels * BOTTOM_PADDING_PERCENTAGE).toInt() + bottomNavBarHeight
        findViewById<ConstraintLayout>(R.id.cl_home).setPadding(0, Utils.toPx(22), 0, bottomPadding)
    }

    companion object {
        const val NAVIGATION_BAR_HEIGHT = "navigation_bar_height"
        const val DIMEN = "dimen"
        const val ANDROID = "android"
        const val BOTTOM_PADDING_PERCENTAGE = 0.015
    }
}