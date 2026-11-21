package com.example.app_en_desarollo.network
import com.example.app_en_desarollo.model.EmpresaLoginRequest
import com.example.app_en_desarollo.model.EmpresaModel
import com.example.app_en_desarollo.model.EmpresaResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface EmpresaApi {

    @POST("api/v1/empresa")
    suspend fun registrar(@Body empresa: EmpresaModel): EmpresaResponse

    @POST("api/v1/empresa/login")
    suspend fun login(@Body credenciales: EmpresaLoginRequest): EmpresaResponse
}