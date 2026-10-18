package com.matias.paoliciassweet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.matias.paoliciassweet.data.local.dao.ReservaDao
import com.matias.paoliciassweet.data.local.entity.ReservaEntity

@Database(entities = [ReservaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){


    abstract fun reservaDao(): ReservaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Paolicias_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}