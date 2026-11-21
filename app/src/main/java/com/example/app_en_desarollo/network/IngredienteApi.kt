package com.example.app_en_desarollo.network

import com.example.app_en_desarollo.model.IngredienteRequest
import com.example.app_en_desarollo.model.IngredienteResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IngredienteApi {

    @GET("api/v1/ingrediente")
    suspend fun listar(): List<IngredienteResponse>

    @POST("api/v1/ingrediente")
    suspend fun crear(@Body ingrediente: IngredienteRequest): IngredienteResponse

    @PUT("api/v1/ingrediente/{id}")
    suspend fun actualizar(
        @Path("id") id: Long,
        @Body ingrediente: IngredienteRequest
    ): IngredienteResponse

    @DELETE("api/v1/ingrediente/{id}")
    suspend fun eliminar(@Path("id") id: Long)
}