package com.example.app_en_desarollo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
<<<<<<< HEAD
import com.example.app_en_desarollo.data.local.dao.ReservaDao
import com.example.app_en_desarollo.data.local.dao.UsuarioDao
import com.example.app_en_desarollo.data.local.entity.ReservaEntity
import com.example.app_en_desarollo.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, ReservaEntity::class],
=======
import com.example.app_en_desarollo.data.local.dao.UsuarioDao
import com.example.app_en_desarollo.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

<<<<<<< HEAD
    abstract fun reservaDao(): ReservaDao

=======
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "paolicias_sweet_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}