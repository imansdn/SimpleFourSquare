package com.imandroid.simplefoursquare.data.network.dto


import com.google.gson.annotations.SerializedName

data class PlaceSearchDTO(
    @SerializedName("context")
    val placeContext: PlaceContext,
    @SerializedName("results")
    val placeResults: List<PlaceResult>
) {
    data class PlaceContext(
        @SerializedName("geo_bounds")
        val geoBounds: GeoBounds
    ) {
        data class GeoBounds(
            @SerializedName("circle")
            val circle: Circle
        ) {
            data class Circle(
                @SerializedName("center")
                val center: Center,
                @SerializedName("radius")
                val radius: Int // 600
            ) {
                data class Center(
                    @SerializedName("latitude")
                    val latitude: Double, // 41.8781
                    @SerializedName("longitude")
                    val longitude: Double // -87.6298
                )
            }
        }
    }

    data class PlaceResult(
        @SerializedName("categories")
        val placeCategories: List<PlaceCategory>,
        @SerializedName("chains")
        val chains: List<Any>,
        @SerializedName("distance")
        val distance: Int, // 76
        @SerializedName("fsq_id")
        val fsqId: String, // 4b087e6ef964a520040d23e3
        @SerializedName("geocodes")
        val geocodes: Geocodes,
        @SerializedName("location")
        val location: Location,
        @SerializedName("name")
        val name: String, // Garrett Popcorn Shops
        @SerializedName("related_places")
        val relatedPlaces: RelatedPlaces,
        @SerializedName("timezone")
        val timezone: String // America/Chicago
    ) {
        data class PlaceCategory(
            @SerializedName("icon")
            val icon: Icon,
            @SerializedName("id")
            val id: Int, // 13382
            @SerializedName("name")
            val name: String // Snack Place
        ) {
            data class Icon(
                @SerializedName("prefix")
                val prefix: String, // https://ss3.4sqi.net/img/categories_v2/food/snacks_
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
                val latitude: Double, // 41.878174543159915
                @SerializedName("longitude")
                val longitude: Double // -87.6288823334721
            )
        }

        data class Location(
            @SerializedName("address")
            val address: String, // 27 W Jackson Blvd
            @SerializedName("address_extended")
            val addressExtended: String, // Fl 8
            @SerializedName("country")
            val country: String, // US
            @SerializedName("cross_street")
            val crossStreet: String, // South Plymouth Court
            @SerializedName("dma")
            val dma: String, // Chicago
            @SerializedName("locality")
            val locality: String, // Chicago
            @SerializedName("neighborhood")
            val neighborhood: List<String>,
            @SerializedName("postcode")
            val postcode: String, // 60604
            @SerializedName("region")
            val region: String // IL
        )

        data class RelatedPlaces(
            @SerializedName("children")
            val children: List<Children>,
            @SerializedName("parent")
            val parent: Parent
        ) {
            data class Children(
                @SerializedName("fsq_id")
                val fsqId: String, // 52ffda0c498e7d4d814cce10
                @SerializedName("name")
                val name: String // Maker Lab
            )

            data class Parent(
                @SerializedName("fsq_id")
                val fsqId: String, // 4ac9453ef964a52074bf20e3
                @SerializedName("name")
                val name: String // Hotel Blake
            )
        }
    }
}