package com.imandroid.simplefoursquare.data.db

import com.imandroid.simplefoursquare.data.dataSource.ExploreDBDataSource
import com.imandroid.simplefoursquare.data.db.dao.ExploreDao
import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import io.reactivex.Maybe
import io.reactivex.Single

class ExploreDbDataImpl(private val dao: ExploreDao):ExploreDBDataSource {

    override fun getAllExplores(limit:String ,pageNumber:String): Maybe<List<ExploreEntity>> {
         val equivalentOffset = (limit.toInt() * (pageNumber.toInt()+1)) - limit.toInt()
        return dao.getAllExplores(limit = limit.toInt(), offset = equivalentOffset)
    }

    override fun getExploreById(explore_id: String): Maybe<ExploreEntity> {
        return dao.getExploreByID(explore_id=explore_id)
    }

    override fun updateExplore(exploreEntity: ExploreEntity):Int {
       return dao.update(exploreEntity)
    }

    override fun getExplorePrimaryKeyByID(explore_id: String): Single<Int> {
        return dao.getExplorePrimaryKeyByID(explore_id)
    }

    override fun getAllExploresCount(): Single<Int> {
        return dao.getAllExploresCount()
    }

    override fun addAllExplores(explores: List<ExploreEntity>) {
        dao.insertAll(explores = explores)
    }

    override fun clearAllExplores() {
        dao.deleteAll()
    }


}