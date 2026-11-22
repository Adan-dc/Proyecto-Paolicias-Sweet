package com.example.app_en_desarollo.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming

data class ComprobanteBackend(
    val id: Long,
    val pagoId: Long,
    val nombreArchivo: String? = null,
    val tipoArchivo: String? = null,
    val fechaSubida: String? = null,
    val estadoComprobante: String? = null,
    val observacionAdmin: String? = null
)

interface ComprobanteApi {

    @Multipart
    @POST("api/comprobantes/subir")
    suspend fun subirComprobante(
        @Part("pagoId") pagoId: RequestBody,
        @Part archivo: MultipartBody.Part
    ): ComprobanteBackend

    @GET("api/comprobantes")
    suspend fun obtenerTodos(): List<ComprobanteBackend>

    @GET("api/comprobantes/{id}")
    suspend fun obtenerPorId(
        @Path("id") id: Long
    ): ComprobanteBackend

    @GET("api/comprobantes/pago/{pagoId}")
    suspend fun obtenerPorPago(
        @Path("pagoId") pagoId: Long
    ): List<ComprobanteBackend>

    @Streaming
    @GET("api/comprobantes/{id}/archivo")
    suspend fun descargarArchivo(
        @Path("id") id: Long
    ): ResponseBody

    @PUT("api/comprobantes/{id}/aprobar")
    suspend fun aprobarComprobante(
        @Path("id") id: Long
    ): ComprobanteBackend

    @PUT("api/comprobantes/{id}/rechazar")
    suspend fun rechazarComprobante(
        @Path("id") id: Long,
        @Body motivo: RequestBody
    ): ComprobanteBackend
}
