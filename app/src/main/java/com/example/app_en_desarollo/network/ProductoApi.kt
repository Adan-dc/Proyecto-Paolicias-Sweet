package com.example.app_en_desarollo.network

import com.example.app_en_desarollo.model.ProductoRequest
import com.example.app_en_desarollo.model.ProductoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoApi {

    @GET("api/v1/producto")
    suspend fun listar(): List<ProductoResponse>

    @POST("api/v1/producto")
    suspend fun crear(@Body producto: ProductoRequest): ProductoResponse

    @PUT("api/v1/producto/{id}")
    suspend fun actualizar(
        @Path("id") id: Long,
        @Body producto: ProductoRequest
    ): ProductoResponse

    @DELETE("api/v1/producto/{id}")
    suspend fun eliminar(@Path("id") id: Long)

    @POST("api/v1/producto/{id}/ingrediente/{ingredienteId}")
    suspend fun agregarIngrediente(
        @Path("id") id: Long,
        @Path("ingredienteId") ingredienteId: Long,
        @Query("cantidad") cantidad: Double
    ): ProductoResponse

    @DELETE("api/v1/producto/{id}/ingrediente/{ingredienteId}")
    suspend fun quitarIngrediente(
        @Path("id") id: Long,
        @Path("ingredienteId") ingredienteId: Long
    ): ProductoResponse
}