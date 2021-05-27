package com.example.ultimateencyclopedia.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.ultimateencyclopedia.utils.UltimateEnums.Companion.CACHE_TIMESTAMP
import com.example.ultimateencyclopedia.utils.UltimateEnums.Companion.DATA_STORE_NAME

class DataStore {
    companion object {

        private lateinit var sharedPreferences: SharedPreferences

        private fun getSharedPreferences(context: Context): SharedPreferences {
            if (!this::sharedPreferences.isInitialized) {
                sharedPreferences = context.getSharedPreferences(DATA_STORE_NAME, MODE_PRIVATE)
            }
            return sharedPreferences
        }

        fun storeCacheTimestamp(timestamp: Long, context: Context) {
            getSharedPreferences(context).edit().putLong(CACHE_TIMESTAMP, timestamp).apply()
        }

        fun getCacheTimestamp(context: Context): Long {
            return getSharedPreferences(context).getLong(CACHE_TIMESTAMP, -1L)
        }
    }
}