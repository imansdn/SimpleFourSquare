package com.imandroid.simplefoursquare.data.dataSource

import com.imandroid.simplefoursquare.domain.ExploreModel
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface ExploreDataSource {

    fun getAllExplores(isNeedClear:Boolean,latlong:String ,pageNumber:String): Maybe<List<ExploreModel>>

    fun getExploreById(exploreId:String):Maybe<ExploreModel>

    fun clear()

}