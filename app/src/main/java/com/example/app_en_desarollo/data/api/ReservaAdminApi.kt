package com.example.app_en_desarollo.data.api

import com.example.app_en_desarollo.model.ReservaAdminModel
import retrofit2.http.GET

interface ReservaAdminApi {

    @GET("api/reservas")
    suspend fun listarReservas(): List<ReservaAdminModel>
}