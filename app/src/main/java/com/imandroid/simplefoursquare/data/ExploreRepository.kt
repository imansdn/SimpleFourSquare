package com.imandroid.simplefoursquare.data

import android.annotation.SuppressLint
import com.imandroid.simplefoursquare.data.dataSource.ExploreDataSource
import com.imandroid.simplefoursquare.data.db.ExploreDbDataImpl
import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import com.imandroid.simplefoursquare.data.network.ExploreApiDataImpl
import com.imandroid.simplefoursquare.data.sharedPref.SharedPrefHelper
import com.imandroid.simplefoursquare.domain.ExploreModel
import com.imandroid.simplefoursquare.util.*
import com.imandroid.simplefoursquare.util.PaginationListener.Companion.PAGE_SIZE
import com.imandroid.simplefoursquare.util.extension.disposedBy
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ExploreRepository private constructor(private val api: ExploreApiDataImpl ,
    private val db: ExploreDbDataImpl ,
    private val sharedPrefHelper: SharedPrefHelper
) : ExploreDataSource {
    var bag = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getAllExplores(isNeedClear: Boolean, latlong: String, pageNumber: String): Maybe<List<ExploreModel>> {
        return if (isNeedClear) {
            getAllExploresFromApiSaveToDB(isNeedClear, latlong, pageNumber)
        } else {
            Maybe.concat(
                getAllExploresFromDb(pageNumber)
                , getAllExploresFromApiSaveToDB(isNeedClear, latlong, pageNumber)
            )
                .filter { it.isNotEmpty() }
                .firstElement()
        }

    }

    override fun getExploreById(exploreId: String): Maybe<ExploreModel> {

        return getExploreByIdFromApiSaveToDB(exploreId)
    }

    private fun getAllExploresFromApiSaveToDB(isNeedClear: Boolean, latlong: String, offset: String): Maybe<List<ExploreModel>> {

        return api.getAllExplores(latlong, PAGE_SIZE.toString(), offset)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSuccess {
                Timber.d("Dispatching ${it!!.response.groups[0].items.size} explores from API...")
                if (it.response.groups[0].items.isNotEmpty()) {

                    val totalResult = it.response.totalResults

                    val totalPage =
                        if (totalResult % PAGE_SIZE == 0) totalResult / PAGE_SIZE else (totalResult / PAGE_SIZE + 1)

                    /** update totalPage  */
                    sharedPrefHelper.write(SH_TOTAL_PAGE, totalPage)

                    val isLastPage =
                        if (totalPage > 0) (offset.toInt() >= (totalPage - 1)) else false

                    /** update isLastPage  */
                    sharedPrefHelper.write(SH_IS_LAST_PAGE, isLastPage)

                    Timber.i("current page = $offset")
                    Timber.i("total page = $totalPage")
                    Timber.i("isLastPage = $isLastPage")

                    /** clear all explores   */
                    if (isNeedClear) {
                        db.clearAllExplores()
                        Timber.i("clear all explores")
                    }
                    storeUsersInDb(expDtoToListExpEntity(it))
                }
            }
            .doOnError {
                Timber.e("can not get the result from get all explores. error = ${it.message}")
            }.map { expDtoToListExpModel(it) }


    }

    private fun getAllExploresFromDb(offset: String): Maybe<List<ExploreModel>> {
        val totalPage = sharedPrefHelper.read(SH_TOTAL_PAGE, 1)
        val isLastPage = if (totalPage > 0) (offset.toInt() >= (totalPage - 1)) else false
        /** update isLastPage  */
        sharedPrefHelper.write(SH_IS_LAST_PAGE, isLastPage)

        Timber.i("current page = $offset")
        Timber.i("total page = $totalPage")
        Timber.i("isLastPage = $isLastPage")

        return db.getAllExplores(limit = PAGE_SIZE.toString(), pageNumber = offset)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSuccess {
                Timber.d("Dispatching ${it?.size} explores from DB...")
            }
            .map { listXtoListY(it, ::expEntityToExpModel) }

    }

    private fun storeUsersInDb(explores: List<ExploreEntity>) {
        Maybe
            .fromCallable {
                db.addAllExplores(explores)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Timber.d("Inserted ${explores.size} explores from API in DB...")
            }.disposedBy(bag)
    }

    private fun getExploreByIdFromApiSaveToDB(explore_id: String): Maybe<ExploreModel> {
        //get from api and update in db
        return api.getExploreDetails(explore_id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSuccess {
                Timber.d("Dispatching explore \"${it.response.venue.name}\" from API...")
                db.getExplorePrimaryKeyByID(explore_id).subscribe({ primaryKey ->
                    Timber.i("the primary key is $primaryKey")
                    val exploreEntity = exploreDetailsDtoToExpEntity(it)
                    exploreEntity.id = primaryKey.toLong()
                    var rowsUpdated = db.updateExplore(exploreEntity)
                    Timber.d("Updated explore ${exploreEntity.name}  from API in DB.../rowsUpdated = $rowsUpdated")

//                     updateExploreInDB(exploreDetailsDtoToExpEntity(it),primaryKey)

                }, {
                    Timber.e("can not Dispatching explore id \"${explore_id}\" from DB - error =${it.message}  ")
                }).disposedBy(bag)

            }.map { expEntityToExpModel(exploreDetailsDtoToExpEntity(it)) }


    }

    private fun getExploreByIdFromDB(explore_id: String): Maybe<ExploreModel> {
        return db.getExploreById(explore_id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSuccess { Timber.d("Dispatching explore \"${it?.name}\" and likes=${it?.likes} from DB...") }
            .map { expEntityToExpModel(it) }
    }


    override fun clear() {
        bag.dispose()
        bag.clear()
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ExploreRepository? = null

        fun getInstance(api: ExploreApiDataImpl ,
                        db: ExploreDbDataImpl ,
                        sharedPrefHelper: SharedPrefHelper ) =
            instance ?: synchronized(this) {
                instance ?: ExploreRepository(api, db, sharedPrefHelper).also { instance = it }
            }
    }

}