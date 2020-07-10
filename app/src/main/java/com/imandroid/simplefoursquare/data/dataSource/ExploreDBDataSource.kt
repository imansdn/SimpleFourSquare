package com.imandroid.simplefoursquare.data.dataSource

import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import com.imandroid.simplefoursquare.data.network.dto.GetExploreDetailsDTO
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface ExploreDBDataSource {

    fun getAllExplores(limit:String ,pageNumber:String): Maybe<List<ExploreEntity>>

    fun getExploreById(explore_id:String): Maybe<ExploreEntity>

    fun updateExplore(exploreEntity: ExploreEntity):Int

    fun getExplorePrimaryKeyByID(explore_id:String): Single<Int>

    fun getAllExploresCount(): Single<Int>

    fun addAllExplores(explores:List<ExploreEntity>)

    fun clearAllExplores()

}