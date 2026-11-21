package com.example.app_en_desarollo.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGeneral {

    private const val IP = "192.168.1.53"

    private val retrofitTrabajadores by lazy {
        Retrofit.Builder()
            .baseUrl("http://$IP:8092/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitReservas by lazy {
        Retrofit.Builder()
            .baseUrl("http://$IP:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitCosto by lazy {

        Retrofit.Builder()
            .baseUrl("http://$IP:8093/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
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