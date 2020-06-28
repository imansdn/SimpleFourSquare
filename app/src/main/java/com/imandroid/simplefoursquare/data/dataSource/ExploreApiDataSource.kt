package com.imandroid.simplefoursquare.data.dataSource

import com.imandroid.simplefoursquare.data.network.dto.GetAllExploresDTO
import io.reactivex.Flowable
import io.reactivex.Maybe

interface ExploreApiDataSource {

    fun getAllExplores(latlong:String ,limit:String ,offset:String): Maybe<GetAllExploresDTO>

}