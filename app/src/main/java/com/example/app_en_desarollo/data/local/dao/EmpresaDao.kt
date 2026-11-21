package com.example.app_en_desarollo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app_en_desarollo.data.local.entity.EmpresaEntity

@Dao
interface EmpresaDao {

    @Insert
    suspend fun insertarEmpresa(empresa: EmpresaEntity)

    @Query("""
        SELECT * FROM empresa
        WHERE rut = :rut
        LIMIT 1
    """)
    suspend fun buscarPorRut(rut: String): EmpresaEntity?

    @Query("""
        SELECT empresa.*
        FROM empresa
        INNER JOIN usuarios
        ON empresa.idUsuario = usuarios.idUsuario
        WHERE empresa.rut = :rut
        AND usuarios.password = :password
        LIMIT 1
    """)
    suspend fun loginEmpresa(
        rut: String,
        password: String
    ): EmpresaEntity?
}