package com.imandroid.simplefoursquare.data.network

import com.imandroid.simplefoursquare.data.dataSource.ExploreApiDataSource
import com.imandroid.simplefoursquare.data.network.dto.GetAllExploresDTO
import io.reactivex.Flowable
import io.reactivex.Maybe

class ExploreApiDataImpl:ExploreApiDataSource {
    private val api: Api = ApiGenerator.createService(Api::class.java)

    override fun getAllExplores(latlong: String, limit: String, offset: String): Maybe<GetAllExploresDTO> {
        return api.getAllExploresByLatLng(latlong = latlong,limit = limit ,offset = offset )
    }



}