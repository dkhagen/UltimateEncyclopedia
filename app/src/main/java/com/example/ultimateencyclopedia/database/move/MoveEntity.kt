package com.example.ultimateencyclopedia.database.move

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moves")
data class MoveEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "fighter") val fighter: String?,
    @ColumnInfo(name = "move_input") val input: String?,
    @ColumnInfo(name = "move_damage") val damage: String,
    @ColumnInfo(name = "move_name") val name: String?,
    @ColumnInfo(name = "move_description") val description: String?,
    @ColumnInfo(name = "move_hitbox") val hitbox: String?,
)