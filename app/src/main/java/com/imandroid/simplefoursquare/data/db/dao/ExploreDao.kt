package com.imandroid.simplefoursquare.data.db.dao

import androidx.room.*
import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ExploreDao {

    @Query("SELECT * FROM explores_table LIMIT :limit OFFSET :offset")
    fun getAllExplores(limit:Int,offset:Int): Maybe<List<ExploreEntity>>


    @Query("SELECT * FROM explores_table where explore_id = :explore_id")
    fun getExploreByID(explore_id:String): Maybe<ExploreEntity>

    @Query("SELECT id FROM explores_table where explore_id = :explore_id")
    fun getExplorePrimaryKeyByID(explore_id:String): Single<Int>

    @Query("SELECT COUNT(ID) FROM explores_table")
    fun getAllExploresCount(): Single<Int>



    @Query("update explores_table set likes= :likes  and rating=:rating and ratingColor=:rating_color and createdAt=:createdAt and shortUrl=:shortUrl and categories=:categories  and photos=:photos and tips=:tips   where explore_id= :explore_id")
    fun update(explore_id:String,likes:String,rating:String,rating_color:String,createdAt:String,shortUrl:String,categories:String, photos:String ,tips:String)


    @Update
    fun update(exploreEntity: ExploreEntity):Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(explores: List<ExploreEntity>)

    @Delete
    fun deleteAll(explores: List<ExploreEntity>)

    @Query("DELETE FROM explores_table")
    fun deleteAll()
}