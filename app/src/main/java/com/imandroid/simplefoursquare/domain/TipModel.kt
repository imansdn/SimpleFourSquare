package com.imandroid.simplefoursquare.domain

import android.os.Parcelable
import androidx.room.TypeConverters
import com.imandroid.simplefoursquare.util.Converters
import kotlinx.android.parcel.Parcelize

@Parcelize
@TypeConverters(Converters::class)
data class TipModel(
    val message: String = "",
    val firstName: String = "",
    val lastName: String,
    val userAvatarUrl: String = "",
    val fullName:String = "$firstName $lastName"
):Parcelable