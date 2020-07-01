package com.imandroid.simplefoursquare.data.dataSource

import com.imandroid.simplefoursquare.data.network.dto.GetAllExploresDTO
import com.imandroid.simplefoursquare.data.network.dto.GetExploreDetailsDTO
import io.reactivex.Flowable
import io.reactivex.Maybe

interface ExploreApiDataSource {

    fun getAllExplores(latlong:String ,limit:String ,offset:String): Maybe<GetAllExploresDTO>

    fun getExploreDetails(explore_id:String): Maybe<GetExploreDetailsDTO>

}