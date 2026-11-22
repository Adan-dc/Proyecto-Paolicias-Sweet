package com.example.app_en_desarollo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app_en_desarollo.data.local.dao.ReservaDao
import com.example.app_en_desarollo.data.local.dao.UsuarioDao
import com.example.app_en_desarollo.data.local.entity.ReservaEntity
import com.example.app_en_desarollo.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, ReservaEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    abstract fun reservaDao(): ReservaDao

    companion object {

        // Conserva las reservas anteriores al añadir el comprobante de abono.
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE reservas ADD COLUMN comprobanteUri TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE reservas ADD COLUMN estadoAbono TEXT NOT NULL DEFAULT 'sin_comprobante'")
            }
        }

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "paolicias_sweet_database"
                )
                    .addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}