package com.example.ultimateencyclopedia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ultimateencyclopedia.database.move.MoveDao
import com.example.ultimateencyclopedia.database.move.MoveEntity
import com.example.ultimateencyclopedia.database.fighter.FighterDao
import com.example.ultimateencyclopedia.database.fighter.FighterEntity

@Database(entities = [FighterEntity::class, MoveEntity::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class UltimateDatabase : RoomDatabase() {
    abstract fun fighterDao(): FighterDao?
    abstract fun moveDao(): MoveDao?

    companion object {
        private var instance: UltimateDatabase? = null

        fun getUltimateDatabase(context: Context): UltimateDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    UltimateDatabase::class.java,
                    "UltimateDatabase"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}