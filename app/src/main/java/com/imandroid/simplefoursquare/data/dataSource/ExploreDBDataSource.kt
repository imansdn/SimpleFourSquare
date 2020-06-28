package com.imandroid.simplefoursquare.data.dataSource

import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

interface ExploreDBDataSource {

    fun getAllExplores(limit:String ,offset:String): Flowable<List<ExploreEntity>>

    fun addAllExplores(explores:List<ExploreEntity>)

    fun clearAllExplores()

}