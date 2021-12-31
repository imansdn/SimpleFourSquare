package com.imandroid.simplefoursquare.data.network

import com.imandroid.simplefoursquare.BuildConfig
import com.imandroid.simplefoursquare.data.network.dto.GetAllExploresDTO
import com.imandroid.simplefoursquare.data.network.dto.GetExploreDetailsDTO
import com.imandroid.simplefoursquare.util.CLIENT_ID
import com.imandroid.simplefoursquare.util.CLIENT_SECRET
import com.imandroid.simplefoursquare.util.VERSION_API
import io.reactivex.Flowable
import io.reactivex.Maybe

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface Api {
    @GET("explore")
    fun getAllExploresByName(
        @Query("client_id") client_id: String = CLIENT_ID,
        @Query("client_secret") client_secret: String = CLIENT_SECRET,
        @Query("v") v: String = VERSION_API,
        @Query("limit") limit: String,
        @Query("near") near: String,
        @Query("offset") offset: String
    ): Maybe<GetAllExploresDTO>

    @GET("explore")
    fun getAllExploresByLatLng(
        @Query("client_id") client_id: String = CLIENT_ID,
        @Query("client_secret") client_secret: String = CLIENT_SECRET,
        @Query("v") v: String = VERSION_API,
        @Query("ll") latlong: String,
        @Query("limit") limit: String,
        @Query("offset") offset: String
    ): Maybe<GetAllExploresDTO>


    @GET("{explore_id}")
    fun getExploreDetailsById(
        @Path("explore_id") explore_id: String,
        @Query("client_id") client_id: String = CLIENT_ID,
        @Query("client_secret") client_secret: String = CLIENT_SECRET,
        @Query("v") v: String = VERSION_API
    ): Maybe<GetExploreDetailsDTO>


}
