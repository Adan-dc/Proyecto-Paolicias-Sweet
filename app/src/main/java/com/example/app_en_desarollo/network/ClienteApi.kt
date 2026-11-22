package com.example.app_en_desarollo.network

import com.example.app_en_desarollo.model.ClienteModel
import com.example.app_en_desarollo.model.ClienteResponse
import com.example.app_en_desarollo.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ClienteApi {

    @POST("api/v1/cliente")
    suspend fun registrar(@Body cliente: ClienteModel): ClienteResponse

    @POST("api/v1/cliente/login")
    suspend fun login(@Body credenciales: LoginRequest): ClienteResponse
}