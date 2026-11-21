package com.example.app_en_desarollo.network

import com.example.app_en_desarollo.model.CategoriaResponse
import retrofit2.http.GET

interface CategoriaApi {

    @GET("api/v1/categoria")
    suspend fun listar(): List<CategoriaResponse>
}