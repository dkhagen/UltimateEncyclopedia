package com.example.ultimateencyclopedia.database

import androidx.room.TypeConverter
import com.example.ultimateencyclopedia.database.move.MoveEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMoveListToString(moveList: List<MoveEntity>): String {
        val type = object : TypeToken<List<MoveEntity>>() {}.type
        return gson.toJson(moveList, type)
    }

    @TypeConverter
    fun fromJsonStringToMoveList(jsonString: String): List<MoveEntity> {
        val type = object : TypeToken<List<MoveEntity>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    @TypeConverter
    fun fromDoubleListToString(doubleList: List<Double>): String {
        val type = object : TypeToken<List<Double>>() {}.type
        return gson.toJson(doubleList, type)
    }

    @TypeConverter
    fun fromStringToDoubleList(jsonString: String) : List<Double> {
        val type = object : TypeToken<List<Double>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}