package com.imandroid.simplefoursquare.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExploreModel(
    val name: String? ="",
    val explore_id:String="-1",
    val lat:Double?=0.0,
    val lng:Double?=0.0,
    val address:String?="",
    val city:String?="",
    val state:String?="",
    val country:String?="",
    val categories:List<CategoryModel> = listOf(),
    var photos:List<String>? = listOf(),
    val likes:String?="",
    val rating:String?="",
    val ratingColor:String?="",
    val createdAt:String?="",
    val tips:List<TipModel> = listOf(),
    val shortUrl:String? = ""


                        ):Parcelable{

    fun seperateName(): String {

        if (name != null) {
            return (if ( name.contains("|")){
                name.split("|")[0].plus("\n").plus(name.split("|")[1])
            }else{
                name
            })
        }
        return ""
    }
}