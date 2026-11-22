package com.example.app_en_desarollo.data.api

import com.example.app_en_desarollo.model.CostoCalculoModel
import com.example.app_en_desarollo.model.ProductoCostoModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CostoApi {
    @GET("api/v1/costo")
    suspend fun listarCostos(): List<CostoCalculoModel>


    @GET("api/v1/costo/detalle/{idReserva}")
    suspend fun obtenerDetalleCosto(
        @Path("idReserva") idReserva: Long
    ): List<ProductoCostoModel>


    @POST("api/v1/costo/calculo")
    suspend fun calcularCosto(
        @Body datos: Map<String, Long>
    ): CostoCalculoModel


    @DELETE("api/v1/costo/{id}")
    suspend fun eliminarCosto(
        @Path("id") id: Long
    )
}