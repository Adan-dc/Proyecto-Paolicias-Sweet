package com.example.app_en_desarollo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_en_desarollo.data.local.dao.ClienteDao
import com.example.app_en_desarollo.data.local.dao.EmpresaDao
import com.example.app_en_desarollo.data.local.dao.ReservaDao
import com.example.app_en_desarollo.data.local.dao.UsuarioDao
import com.example.app_en_desarollo.data.local.entity.ClienteEntity
import com.example.app_en_desarollo.data.local.entity.EmpresaEntity
import com.example.app_en_desarollo.data.local.entity.ReservaEntity
import com.example.app_en_desarollo.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, ReservaEntity::class, ClienteEntity::class, EmpresaEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    abstract fun reservaDao(): ReservaDao

    abstract fun clienteDao(): ClienteDao

    abstract fun empresaDao(): EmpresaDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "paolicias_sweet_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}