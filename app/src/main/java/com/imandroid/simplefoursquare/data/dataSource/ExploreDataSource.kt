package com.imandroid.simplefoursquare.data.dataSource

import com.imandroid.simplefoursquare.domain.ExploreModel
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable

interface ExploreDataSource {

    fun getAllExplores(isNeedClear:Boolean,latlong:String ,offset:String): Flowable<List<ExploreModel>>


}