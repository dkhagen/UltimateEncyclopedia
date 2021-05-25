package com.example.ultimateencyclopedia.database.fighter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fighters")
data class FighterEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "fighter_name") val name: String?,
    @ColumnInfo(name = "fighter_series") val series: String?,
    @ColumnInfo(name = "fighter_rank") val rank: Int?,
    @ColumnInfo(name = "fighter_image") val image: String?,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false
)