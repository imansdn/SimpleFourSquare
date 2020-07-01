package com.imandroid.simplefoursquare.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data  class TipEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0L,
    val message: String? = "",
    val firstName: String? = "",
    val lastName: String?="",
    val userAvatarUrl: String? = ""
)