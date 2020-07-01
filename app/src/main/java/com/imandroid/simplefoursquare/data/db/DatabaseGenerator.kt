package com.imandroid.simplefoursquare.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imandroid.simplefoursquare.data.db.dao.ExploreDao
import com.imandroid.simplefoursquare.data.db.table.CategoryEntity
import com.imandroid.simplefoursquare.data.db.table.ExploreEntity
import com.imandroid.simplefoursquare.data.db.table.TipEntity
import com.imandroid.simplefoursquare.util.Converters


const val DATABASE_VERSION =20
const val DATABASE_NAME ="simple_4square_db"

@Database(entities = [ExploreEntity::class,TipEntity::class,CategoryEntity::class], version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DatabaseGenerator : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val exploreDao: ExploreDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseGenerator? = null


        fun getInstance(context: Context): DatabaseGenerator {

            synchronized(this) {

                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext
                        , DatabaseGenerator::class.java
                        , DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}