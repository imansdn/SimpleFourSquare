package com.imandroid.simplefoursquare.data.network

import com.imandroid.simplefoursquare.data.dataSource.ExploreApiDataSource
import com.imandroid.simplefoursquare.data.network.dto.GetAllExploresDTO
import com.imandroid.simplefoursquare.data.network.dto.GetExploreDetailsDTO
import io.reactivex.Maybe


class ExploreApiDataImpl:ExploreApiDataSource {
    private val api: Api = ApiGenerator.createService(Api::class.java)

    override fun getAllExplores(latlong: String, limit: String, pageNumber: String): Maybe<GetAllExploresDTO> {
        val equivalentOffset = (limit.toInt() * (pageNumber.toInt()+1)) - limit.toInt()

        return api.getAllExploresByLatLng(latlong = latlong,limit = limit ,offset = equivalentOffset.toString() )
    }

    override fun getExploreDetails(explore_id: String): Maybe<GetExploreDetailsDTO> {
        return api.getExploreDetailsById(explore_id=explore_id)
    }


}