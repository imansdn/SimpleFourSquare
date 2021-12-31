package com.imandroid.simplefoursquare.data.network

import com.imandroid.simplefoursquare.data.network.dto.PlaceDetailsDTO
import com.imandroid.simplefoursquare.data.network.dto.PlaceSearchDTO
import com.imandroid.simplefoursquare.data.network.mock.MOCKUP
import com.imandroid.simplefoursquare.util.CLIENT_ID
import com.imandroid.simplefoursquare.util.CLIENT_SECRET
import com.imandroid.simplefoursquare.util.VERSION_API
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {
    @MOCKUP("200","nearbyPlaces.json")
    @GET("places/search")
    fun getAllPlacesByLatLng(
        @Query("ll") latlong: String,
        @Query("limit") limit: String
    ): Maybe<PlaceSearchDTO>


    @MOCKUP("200","PlaceDetails.json")
    @GET("places/{fsq_id}")
    fun getPlaceDetailsById(
        @Query("fsq_id") explore_id: String
    ): Maybe<PlaceDetailsDTO>


}
