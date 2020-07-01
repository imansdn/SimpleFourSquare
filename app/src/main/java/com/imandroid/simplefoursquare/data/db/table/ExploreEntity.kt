package com.imandroid.simplefoursquare.data.db.table

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.imandroid.simplefoursquare.util.Converters

@Entity(tableName = "explores_table")
//@Entity(tableName = "explores_table",indices =[Index(value = ["explore_id"], unique = true)])
@TypeConverters(Converters::class)
data class ExploreEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val explore_id: String,
    val name: String?,
    val lat: Double?,
    val lng: Double?,
    val address: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val categories: List<CategoryEntity>,
    val photos: List<String>?,
    val likes:String?="",
    val rating:String?="",
    val ratingColor:String?="",
    val createdAt:String?="",
    val tips:List<TipEntity> = listOf(),
    val shortUrl:String? = ""
)