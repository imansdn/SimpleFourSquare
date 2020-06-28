package com.imandroid.simplefoursquare.data

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.imandroid.simplefoursquare.data.dataSource.ExploreDataSource
import com.imandroid.simplefoursquare.data.db.ExploreDbDataImpl
import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import com.imandroid.simplefoursquare.data.network.ExploreApiDataImpl
import com.imandroid.simplefoursquare.data.sharedPref.SharedPrefHelper
import com.imandroid.simplefoursquare.domain.ExploreModel
import com.imandroid.simplefoursquare.util.*
import com.imandroid.simplefoursquare.util.PaginationListener.Companion.PAGE_SIZE
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ExploreRepository(private val api:ExploreApiDataImpl, private val db:ExploreDbDataImpl,private val sharedPrefHelper: SharedPrefHelper):ExploreDataSource {

    val totalPageLiveData = MutableLiveData<Int>()
    @SuppressLint("CheckResult")
    override fun getAllExplores(isNeedClear:Boolean, latlong:String, offset:String): Flowable<List<ExploreModel>> {

//        getExploresFromApi(isNeedClear, latlong, offset).subscribe()

        return getExploresFromDb( offset)
    }

    fun getExploresFromApi(isNeedClear:Boolean, latlong:String, offset:String):Maybe<List<ExploreModel>>{

        return api.getAllExplores(latlong, PAGE_SIZE.toString(), offset).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io()).doOnSuccess {
            Timber.d("Dispatching ${it!!.response.groups[0].items.size} explores from API...")
            if (it.response.groups[0].items.isNotEmpty()){

                val totalResult =it.response.totalResults

                val totalPage =
                    if (totalResult % PAGE_SIZE == 0) totalResult / PAGE_SIZE else (totalResult / PAGE_SIZE + 1)

                totalPageLiveData.postValue(totalPage)

                Timber.i("total results is equal to $totalResult")
                Timber.i("total pages is equal to $totalPage")

                if (isNeedClear){
                    db.clearAllExplores()
                }
                storeUsersInDb(expDtoToListExpEntity(it))
            }
        }.map { expDtoToListExpModel(it) }

    }

    fun getExploresFromDb(  offset:String):Flowable<List<ExploreModel>>{

        return db.getAllExplores(PAGE_SIZE.toString(), offset)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnEach { Timber.d("Dispatching ${it.value?.size} explores from DB...") }
            .map { listXtoListY(it,::expEntityToExpModel) }

    }

    fun storeUsersInDb(explores: List<ExploreEntity>) {
        Maybe.fromCallable {db.addAllExplores(explores)}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Timber.d("Inserted ${explores.size} users from API in DB...")
            }
    }





}