package com.imandroid.simplefoursquare.data.network.dto


import com.google.gson.annotations.SerializedName

class PlacePhotosDTO : ArrayList<PlacePhotosDTO.PlacePhotosDTOItem>(){
    data class PlacePhotosDTOItem(
        @SerializedName("classifications")
        val classifications: List<String>,
        @SerializedName("created_at")
        val createdAt: String, // 2021-12-31T23:10:20.042Z
        @SerializedName("height")
        val height: Int, // 0
        @SerializedName("id")
        val id: String, // string
        @SerializedName("prefix")
        val prefix: String, // string
        @SerializedName("suffix")
        val suffix: String, // string
        @SerializedName("width")
        val width: Int // 0
    )
}