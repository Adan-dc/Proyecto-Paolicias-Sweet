package com.example.app_en_desarollo.data.api

import retrofit2.http.GET
import com.example.app_en_desarollo.model.TrabajadorModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TrabajadorApi {
    @GET("api/v1/trabajadores")
    suspend fun listarTrabajadores(): List<TrabajadorModel>

    @POST("api/v1/trabajadores/crear")
    suspend fun crearTrabajador(@Body trabajador: TrabajadorModel): TrabajadorModel

    @PUT("api/v1/trabajadores/{idTrabajador}/asignacion")
    suspend fun asignarEvento(@Path ("idTrabajador") idTrabajador: Long, @Body datos: Map<String, Long>): TrabajadorModel


    @PUT("api/v1/trabajadores/{idTrabajador}/cancelar-asignacion")
    suspend fun cancelarAsignacion(@Path("idTrabajador") idTrabajador: Long): TrabajadorModel

    @PUT("api/v1/trabajadores/{id}")
    suspend fun actualizarTrabajador(@Path ("id") id: Long, @Body trabajador: TrabajadorModel): TrabajadorModel

    @DELETE("api/v1/trabajadores/{id}")
    suspend fun eliminarTrabajador(@Path ("id") id: Long)
}