package com.example.app_en_desarollo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app_en_desarollo.data.local.entity.ClienteEntity

@Dao
interface ClienteDao {

    @Insert
    suspend fun insertarCliente(cliente: ClienteEntity)

    @Query("""
        SELECT * FROM clientes
        WHERE email = :email
        LIMIT 1
    """)
    suspend fun buscarPorEmail(email: String): ClienteEntity?

    @Query("""
        SELECT clientes.*
        FROM clientes
        INNER JOIN usuarios
        ON clientes.idUsuario = usuarios.idUsuario
        WHERE clientes.email = :email
        AND usuarios.password = :password
        LIMIT 1
    """)
    suspend fun loginCliente(
        email: String,
        password: String
    ): ClienteEntity?
}