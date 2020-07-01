package com.imandroid.simplefoursquare.domain

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.TypeConverters
import com.imandroid.simplefoursquare.R
import com.imandroid.simplefoursquare.util.Converters
import kotlinx.android.parcel.Parcelize

@Parcelize
@TypeConverters(Converters::class)
data class CategoryModel(
    val primary: Boolean = true,
    val icon_url: String = "",
    val name: String,
    val pluralName: String = "",
    @DrawableRes val background: Int = R.drawable.ic_launcher_background,
    @DrawableRes val icon: Int= R.drawable.ic_launcher_background
):Parcelable