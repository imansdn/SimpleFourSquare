package com.imandroid.simplefoursquare.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val icon_url: String = "",
    val name: String,
    val pluralName: String = ""
)