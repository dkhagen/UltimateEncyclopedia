package com.example.ultimateencyclopedia.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ultimateencyclopedia.FavoritesActivity
import com.example.ultimateencyclopedia.ListActivityTemp
import com.example.ultimateencyclopedia.R
import com.example.ultimateencyclopedia.database.UltimateDatabase
import com.example.ultimateencyclopedia.search.SearchActivity
import com.example.ultimateencyclopedia.utils.DataStore
import com.example.ultimateencyclopedia.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeActivity : BaseActivity() {
    private lateinit var searchButton: Button
    private lateinit var listButton: Button
    private lateinit var ivSmashLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_home)
        hideStatusBar()
        startBackgroundAnimation()
        bindViews()
        loadHomeImage()
        downloadDataIfNecessary()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        searchButton.setOnClickListener {
            openSearch(it)
        }
        listButton.setOnClickListener {
            openFavorites(it)
        }
    }

    private fun bindViews() {
        searchButton = findViewById(R.id.button_search)
        listButton = findViewById(R.id.button_list)
        ivSmashLogo = findViewById(R.id.iv_home)
    }

    private fun loadHomeImage() {
        Glide.with(this)
            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/Super_Smash_Bros._Ultimate_logo.svg/2560px-Super_Smash_Bros._Ultimate_logo.svg.png")
            .apply(RequestOptions.fitCenterTransform())
            .into(ivSmashLogo)
    }

    private fun downloadDataIfNecessary() {
        GlobalScope.launch(Dispatchers.IO) {
            val shouldDownload = shouldDownloadFighters()
            Log.i(this@HomeActivity.javaClass.name, shouldDownload.toString())
            if (shouldDownload) {
                Utils.deleteAllData(applicationContext)
                Utils.downloadFighters(applicationContext)
                Utils.downloadMoves(applicationContext)
            }
        }
    }

    private suspend fun shouldDownloadFighters(): Boolean {
        val storedTimestamp = DataStore.getCacheTimestamp(applicationContext)
        val remoteTimestamp = Utils.downloadTimestamp(applicationContext)
        DataStore.storeCacheTimestamp(remoteTimestamp, applicationContext)
        return storedTimestamp < remoteTimestamp || storedTimestamp == -1L
    }

    private fun openSearch(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun openList(view: View) {
        val intent = Intent(this, ListActivityTemp::class.java)
        startActivity(intent)
    }

    private fun openFavorites(view: View) {
        val intent = Intent(this, FavoritesActivity::class.java)
        startActivity(intent)
    }
}