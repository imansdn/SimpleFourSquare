package com.imandroid.simplefoursquare.data.dataSource

import com.imandroid.simplefoursquare.data.network.dto.PlaceDetailsDTO
import com.imandroid.simplefoursquare.data.network.dto.PlaceSearchDTO
import io.reactivex.Maybe

interface ExploreApiDataSource {

    fun getAllExplores(latlong:String ,limit:String ,offset:String): Maybe<PlaceSearchDTO>

    fun getPlaceDetails(explore_id:String): Maybe<PlaceDetailsDTO>

}