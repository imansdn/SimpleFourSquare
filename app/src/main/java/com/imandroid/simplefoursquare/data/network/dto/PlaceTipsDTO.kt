package com.imandroid.simplefoursquare.data.network.dto


import com.google.gson.annotations.SerializedName

class PlaceTipsDTO : ArrayList<PlaceTipsDTO.PlaceTipsDTOItem>(){
    data class PlaceTipsDTOItem(
        @SerializedName("agree_count")
        val agreeCount: Int, // 0
        @SerializedName("created_at")
        val createdAt: String, // 2021-12-31T23:21:06.331Z
        @SerializedName("disagree_count")
        val disagreeCount: Int, // 0
        @SerializedName("id")
        val id: String, // string
        @SerializedName("text")
        val text: String, // string
        @SerializedName("url")
        val url: String // string
    )
}