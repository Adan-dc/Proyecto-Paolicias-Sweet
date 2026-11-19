package com.example.app_en_desarollo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app_en_desarollo.data.local.entity.UserEntity

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertarUsuario(usuario: UserEntity): Long

    @Query("""
    SELECT * FROM usuarios
    WHERE idUsuario = :idUsuario
    LIMIT 1
    """)
    suspend fun buscarPorId(idUsuario: Int): UserEntity?

}