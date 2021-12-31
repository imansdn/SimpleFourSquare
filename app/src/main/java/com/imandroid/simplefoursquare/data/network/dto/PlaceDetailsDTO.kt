package com.imandroid.simplefoursquare.data.network.dto


import com.google.gson.annotations.SerializedName

data class PlaceDetailsDTO(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("chains")
    val chains: List<Any>,
    @SerializedName("fsq_id")
    val fsqId: String, // 55020690498e6dd512bf18ac
    @SerializedName("geocodes")
    val geocodes: Geocodes,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String, // SoMe Connect
    @SerializedName("related_places")
    val relatedPlaces: RelatedPlaces,
    @SerializedName("timezone")
    val timezone: String // America/Chicago
) {
    data class Category(
        @SerializedName("icon")
        val icon: Icon,
        @SerializedName("id")
        val id: Int, // 11001
        @SerializedName("name")
        val name: String // Advertising Agency
    ) {
        data class Icon(
            @SerializedName("prefix")
            val prefix: String, // https://ss3.4sqi.net/img/categories_v2/building/default_
            @SerializedName("suffix")
            val suffix: String // .png
        )
    }

    data class Geocodes(
        @SerializedName("main")
        val main: Main
    ) {
        data class Main(
            @SerializedName("latitude")
            val latitude: Double, // 41.88291992
            @SerializedName("longitude")
            val longitude: Double // -87.6492826
        )
    }

    data class Location(
        @SerializedName("address")
        val address: String, // 845 W Washington Blvd
        @SerializedName("address_extended")
        val addressExtended: String, // Ste 2
        @SerializedName("country")
        val country: String, // US
        @SerializedName("cross_street")
        val crossStreet: String,
        @SerializedName("dma")
        val dma: String, // Chicago
        @SerializedName("locality")
        val locality: String, // Chicago
        @SerializedName("neighborhood")
        val neighborhood: List<String>,
        @SerializedName("postcode")
        val postcode: String, // 60607
        @SerializedName("region")
        val region: String // IL
    )

    class RelatedPlaces
}