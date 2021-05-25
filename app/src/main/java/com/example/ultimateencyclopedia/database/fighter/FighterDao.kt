package com.example.ultimateencyclopedia.database.fighter

import androidx.room.*

@Dao
interface FighterDao {

    @Query("SELECT * FROM fighters ORDER BY fighter_rank ASC")
    fun getAllFighters(): List<FighterEntity>

    @Query("SELECT * FROM fighters WHERE fighter_name = :fighterName")
    fun getFighterByName(fighterName: String): FighterEntity

    @Query("SELECT * FROM fighters WHERE is_favorite = 1")
    fun getFavoriteFighters(): List<FighterEntity>

    @Insert
    fun insertAll(vararg fighters: FighterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfFighters(list: List<FighterEntity>)

    @Delete
    fun delete(fighter: FighterEntity)

    @Query("DELETE FROM fighters")
    fun deleteAllFighters()

    @Query("UPDATE fighters SET is_favorite = :isFavorite WHERE fighter_name = :fighterName")
    fun setIsFavorite(fighterName: String, isFavorite: Boolean)
}