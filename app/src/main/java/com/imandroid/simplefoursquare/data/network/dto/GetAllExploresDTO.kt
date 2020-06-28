package com.imandroid.simplefoursquare.data.network.dto


import com.google.gson.annotations.SerializedName

data class GetAllExploresDTO(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("response")
    val response: Response
) {
    data class Meta(
        @SerializedName("code")
        val code: Int,
        @SerializedName("requestId")
        val requestId: String
    )

    data class Response(
        @SerializedName("groups")
        val groups: List<Group>,
        @SerializedName("headerFullLocation")
        val headerFullLocation: String,
        @SerializedName("headerLocation")
        val headerLocation: String,
        @SerializedName("headerLocationGranularity")
        val headerLocationGranularity: String,
        @SerializedName("suggestedBounds")
        val suggestedBounds: SuggestedBounds,
        @SerializedName("suggestedFilters")
        val suggestedFilters: SuggestedFilters,
        @SerializedName("suggestedRadius")
        val suggestedRadius: Int,
        @SerializedName("totalResults")
        val totalResults: Int
    ) {
        data class Group(
            @SerializedName("items")
            val items: List<Item1>,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: String
        ) {
            data class Item1(
                @SerializedName("reasons")
                val reasons: Reasons,
                @SerializedName("referralId")
                val referralId: String,
                @SerializedName("venue")
                val venue: Venue
            ) {
                data class Reasons(
                    @SerializedName("count")
                    val count: Int,
                    @SerializedName("items")
                    val items: List<Item2>
                ) {
                    data class Item2(
                        @SerializedName("reasonName")
                        val reasonName: String,
                        @SerializedName("summary")
                        val summary: String,
                        @SerializedName("type")
                        val type: String
                    )
                }

                data class Venue(
                    @SerializedName("categories")
                    val categories: List<Category>,
                    @SerializedName("id")
                    val id: String,
                    @SerializedName("location")
                    val location: Location,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("photos")
                    val photos: Photos
                ) {
                    data class Category(
                        @SerializedName("icon")
                        val icon: Icon,
                        @SerializedName("id")
                        val id: String,
                        @SerializedName("name")
                        val name: String,
                        @SerializedName("pluralName")
                        val pluralName: String,
                        @SerializedName("primary")
                        val primary: Boolean,
                        @SerializedName("shortName")
                        val shortName: String
                    ) {
                        data class Icon(
                            @SerializedName("prefix")
                            val prefix: String,
                            @SerializedName("suffix")
                            val suffix: String

                        )
                    }

                    data class Location(
                        @SerializedName("address")
                        val address: String,
                        @SerializedName("cc")
                        val cc: String,
                        @SerializedName("city")
                        val city: String,
                        @SerializedName("country")
                        val country: String,
                        @SerializedName("distance")
                        val distance: Int,
                        @SerializedName("formattedAddress")
                        val formattedAddress: List<String>,
                        @SerializedName("labeledLatLngs")
                        val labeledLatLngs: List<LabeledLatLng>,
                        @SerializedName("lat")
                        val lat: Double,
                        @SerializedName("lng")
                        val lng: Double,
                        @SerializedName("state")
                        val state: String
                    ) {
                        data class LabeledLatLng(
                            @SerializedName("label")
                            val label: String,
                            @SerializedName("lat")
                            val lat: Double,
                            @SerializedName("lng")
                            val lng: Double
                        )
                    }

                    data class Photos(
                        @SerializedName("count")
                        val count: Int,
                        @SerializedName("groups")
                        val groups: List<Any>
                    )
                }
            }
        }

        data class SuggestedBounds(
            @SerializedName("ne")
            val ne: Ne,
            @SerializedName("sw")
            val sw: Sw
        ) {
            data class Ne(
                @SerializedName("lat")
                val lat: Double,
                @SerializedName("lng")
                val lng: Double
            )

            data class Sw(
                @SerializedName("lat")
                val lat: Double,
                @SerializedName("lng")
                val lng: Double
            )
        }

        data class SuggestedFilters(
            @SerializedName("filters")
            val filters: List<Filter>,
            @SerializedName("header")
            val header: String
        ) {
            data class Filter(
                @SerializedName("key")
                val key: String,
                @SerializedName("name")
                val name: String
            )
        }
    }
}