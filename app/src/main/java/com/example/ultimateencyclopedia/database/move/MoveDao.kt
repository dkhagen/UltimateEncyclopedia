package com.example.ultimateencyclopedia.database.move

import androidx.room.*

@Dao
interface MoveDao {
    @Query("SELECT * FROM moves")
    fun getAllMoves(): List<MoveEntity>

    @Query("SELECT * FROM moves WHERE fighter = :fighterName")
    fun getMovesForFighter(fighterName: String): List<MoveEntity>

    @Query("SELECT * FROM moves WHERE id = :id")
    fun getMoveById(id: Int): MoveEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfMoves(list: List<MoveEntity>)

    @Delete
    fun delete(move: MoveEntity)

    @Query("DELETE FROM moves")
    fun deleteAllMoves()
}