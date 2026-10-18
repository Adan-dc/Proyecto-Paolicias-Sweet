package com.matias.paoliciassweet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matias.paoliciassweet.data.local.entity.ReservaEntity


@Dao
interface ReservaDao{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertReserva(reserva: ReservaEntity)

    @Query("SELECT * FROM reservas WHERE usuarioEmail = :email ORDER BY id DESC")
    suspend fun getReservasPorUsuario(email: String): List<ReservaEntity>
}