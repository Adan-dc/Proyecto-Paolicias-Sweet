package com.example.app_en_desarollo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app_en_desarollo.data.local.entity.UserEntity

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertarUsuario(usuario: UserEntity)

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): UserEntity?

    @Query(
        "SELECT * FROM usuarios " +
                "WHERE email = :email AND password = :password LIMIT 1"
    )
    suspend fun login(
        email: String,
        password: String
    ): UserEntity?
}