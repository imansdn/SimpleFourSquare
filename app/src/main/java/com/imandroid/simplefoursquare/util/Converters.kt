package com.imandroid.simplefoursquare.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.imandroid.simplefoursquare.data.db.table.CategoryEntity
import com.imandroid.simplefoursquare.data.db.table.TipEntity
import com.imandroid.simplefoursquare.domain.CategoryModel
import com.imandroid.simplefoursquare.domain.TipModel


class Converters {

    @TypeConverter
    fun stringToList(value: String?): List<String> {
        if (value == null) {
            return listOf()
        }

        val listType = object : TypeToken<List<Long>>() {}.type

        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(value: List<String>): String {
        return Gson().toJson(value)
    }


    @TypeConverter
    fun stringToCategoryEntity(value: String?): List<CategoryEntity> {
        value?.let {
            val type = object : TypeToken<List<CategoryEntity>>() {}.type

            return Gson().fromJson(value, type)
        }
            ?: return listOf()
    }

    @TypeConverter
    fun categoryEntityToString(value: List<CategoryEntity>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToTipEntity(value: String?): List<TipEntity> {
        value?.let {
            val type = object : TypeToken<List<TipEntity>>() {}.type

            return Gson().fromJson(value, type)
        }
            ?: return listOf()
    }

    @TypeConverter
    fun tipEntityToString(value: List<TipEntity>): String {
        return Gson().toJson(value)
    }

}

