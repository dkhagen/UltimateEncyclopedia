package com.example.ultimateencyclopedia.network

import com.example.ultimateencyclopedia.database.move.MoveEntity
import com.example.ultimateencyclopedia.database.fighter.FighterEntity
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/fighters")
    fun getAllFighters(): Call<List<FighterEntity>>

    @GET("/moves")
    fun getAllMoves(): Call<List<MoveEntity>>

    @GET("/timestamp")
    fun getDataTimestamp(): Call<List<Long>>
}