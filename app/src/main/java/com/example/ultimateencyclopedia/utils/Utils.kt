package com.example.ultimateencyclopedia.utils

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.ultimateencyclopedia.database.UltimateDatabase
import com.example.ultimateencyclopedia.database.UltimateDatabase.Companion.getUltimateDatabase
import com.example.ultimateencyclopedia.database.fighter.FighterEntity
import com.example.ultimateencyclopedia.database.move.MoveEntity
import com.example.ultimateencyclopedia.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class Utils {
    companion object {
        private const val BASE_URL = "https://ultimate-encyclopedia.herokuapp.com"
        private lateinit var api: ApiService

        fun getDamageStringVertical(damageList: List<Double>): String {
            return if (damageList.size == 1) {
                damageList[0].toString() + "%"
            } else {
                var damageString = ""
                for (damageValue in damageList) {
                    damageString += "$damageValue%,\n"
                }
                damageString.dropLast(2)
            }
        }

        fun getDamageStringHorizontal(damageList: List<Double>): String {
            return if (damageList.size == 1) {
                damageList[0].toString() + "%"
            } else {
                var damageString = ""
                for (damageValue in damageList) {
                    damageString += "$damageValue% / "
                }
                damageString.dropLast(3)
            }
        }

        fun deleteAllData(context: Context) {
            getUltimateDatabase(context)?.fighterDao()?.deleteAllFighters()
            getUltimateDatabase(context)?.moveDao()?.deleteAllMoves()
        }

        fun downloadFighters(context: Context) {
            val api = getApiInstance()
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getAllFighters().awaitResponse()
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        insertFighterList(data, context)
                    }
                } catch (e: Exception) {
                    Log.e("Utils", e.toString())
                }
            }
        }

        fun downloadMoves(context: Context) {
            val api = getApiInstance()
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getAllMoves().awaitResponse()
                    if (response.isSuccessful) {
                        val moveList = response.body()!!
                        insertMoveList(moveList, context)
                    }
                } catch (e: Exception) {
                    Log.e("Utils", e.toString())
                }
            }
        }

        suspend fun downloadTimestamp(context: Context): Long {
            var timestamp = -1L
            val api = getApiInstance()
            try {
                val response = api.getDataTimestamp().awaitResponse()
                if (response.isSuccessful) {
                    timestamp = response.body()!![0]
                    // DataStore.storeCacheTimestamp(timestamp, context)
                }
            } catch (e: Exception) {
                Log.e("Utils", e.toString())
            }
            return timestamp
        }

        fun retrieveFighterByName(fighterName: String, context: Context): FighterEntity? {
            val dao = getUltimateDatabase(context)?.fighterDao()
            return dao?.getFighterByName(fighterName)
        }

        fun retrieveMovesForFighter(fighterName: String, context: Context): List<MoveEntity> {
            val dao = getUltimateDatabase(context)?.moveDao()
            return dao?.getMovesForFighter(fighterName) ?: emptyList()
        }

        fun getMoveById(id: Int, context: Context): MoveEntity? {
            val dao = getUltimateDatabase(context)?.moveDao()
            return dao?.getMoveById(id)
        }

        fun setIsFavorite(fighterName: String, isFavorite: Boolean, context: Context) {
            getUltimateDatabase(context)?.fighterDao()?.setIsFavorite(fighterName, isFavorite)
        }

        private fun getApiInstance(): ApiService {
            if (!this::api.isInitialized) {
                api = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            return api
        }

        private fun insertFighterList(data: List<FighterEntity>, context: Context) {
            val dao = getUltimateDatabase(context)?.fighterDao()
            dao?.insertListOfFighters(data)
        }

        private fun insertMoveList(data: List<MoveEntity>, context: Context) {
            val dao = getUltimateDatabase(context)?.moveDao()
            dao?.insertListOfMoves(data)
        }
    }
}