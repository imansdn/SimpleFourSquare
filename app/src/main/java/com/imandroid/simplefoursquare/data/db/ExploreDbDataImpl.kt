package com.imandroid.simplefoursquare.data.db

import com.imandroid.simplefoursquare.data.dataSource.ExploreDBDataSource
import com.imandroid.simplefoursquare.data.db.dao.ExploreDao
import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

class ExploreDbDataImpl(private val dao: ExploreDao):ExploreDBDataSource {

    override fun getAllExplores(limit:String ,offset:String): Flowable<List<ExploreEntity>> {
        return dao.getAllExplores(limit.toInt(),offset.toInt())
    }

    override fun addAllExplores(explores: List<ExploreEntity>) {
        dao.insertAll(explores)
    }

    override fun clearAllExplores() {
        dao.deleteAll()
    }


}