package com.example.app_en_desarollo.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGeneral {

    private const val IP = "server.greengea.cl"

    private val retrofitTrabajadores by lazy {
        Retrofit.Builder()
            .baseUrl("https://server.greengea.cl/paolicias/trabajadores/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitReservas by lazy {
        Retrofit.Builder()
            .baseUrl("https://server.greengea.cl/paolicias/reservas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitCosto by lazy {
        Retrofit.Builder()
            .baseUrl("https://server.greengea.cl/paolicias/costo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val trabajadorApi: TrabajadorApi by lazy {
        retrofitTrabajadores.create(TrabajadorApi::class.java)
    }

    val reservaAdminApi: ReservaAdminApi by lazy {
        retrofitReservas.create(ReservaAdminApi::class.java)
    }

    val costoApi: CostoApi by lazy {
        retrofitCosto.create(CostoApi::class.java)
    }
}