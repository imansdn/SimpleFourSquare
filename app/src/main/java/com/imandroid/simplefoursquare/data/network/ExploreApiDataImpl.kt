package com.imandroid.simplefoursquare.data.network

import com.imandroid.simplefoursquare.data.dataSource.ExploreApiDataSource
import com.imandroid.simplefoursquare.data.network.dto.PlaceDetailsDTO
import com.imandroid.simplefoursquare.data.network.dto.PlaceSearchDTO
import io.reactivex.Maybe


class ExploreApiDataImpl:ExploreApiDataSource {
    private val api: Api = ApiGenerator.createService(Api::class.java)

    override fun getAllExplores(latlong: String, limit: String, pageNumber: String): Maybe<PlaceSearchDTO> {
        val equivalentOffset = (limit.toInt() * (pageNumber.toInt()+1)) - limit.toInt()

        return api.getAllPlacesByLatLng(latlong = latlong,limit = limit)
    }

    override fun getPlaceDetails(fsq_id: String): Maybe<PlaceDetailsDTO> {
        return api.getPlaceDetailsById(explore_id=fsq_id)
    }


}