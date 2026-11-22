package com.example.app_en_desarollo.network

import com.example.app_en_desarollo.model.PackRequest
import com.example.app_en_desarollo.model.PackResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PackApi {

    @GET("api/v1/pack")
    suspend fun listar(): List<PackResponse>

    @POST("api/v1/pack")
    suspend fun crear(@Body pack: PackRequest): PackResponse

    @PUT("api/v1/pack/{id}")
    suspend fun actualizar(
        @Path("id") id: Long,
        @Body pack: PackRequest
    ): PackResponse

    @DELETE("api/v1/pack/{id}")
    suspend fun eliminar(@Path("id") id: Long)

    @POST("api/v1/pack/{id}/producto/{productoId}")
    suspend fun agregarProducto(
        @Path("id") id: Long,
        @Path("productoId") productoId: Long,
        @Query("cantidad") cantidad: Int,
        @Query("grupo") grupo: String? = null
    ): PackResponse

    @DELETE("api/v1/pack/{id}/producto/{productoId}")
    suspend fun quitarProducto(
        @Path("id") id: Long,
        @Path("productoId") productoId: Long
    ): PackResponse
}