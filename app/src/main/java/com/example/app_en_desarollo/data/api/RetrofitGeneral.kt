package com.example.app_en_desarollo.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGeneral {


    private const val IP = "server.greengea.cl"


    private val retrofitReservas by lazy {
        // RESERVAS
        Retrofit.Builder()
            .baseUrl("https://server.greengea.cl/paolicias/reservas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitPagos by lazy {
        // PAGO
        Retrofit.Builder()
            .baseUrl("https://server.greengea.cl/paolicias/pago/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitComprobantes by lazy {
        // COMPROBANTE DE PAGO
        Retrofit.Builder()
            .baseUrl("https://server.greengea.cl/paolicias/comprobante/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val reservaApi: ReservaApi by lazy {
        retrofitReservas.create(ReservaApi::class.java)
    }

    val pagoApi: PagoApi by  lazy{
        retrofitPagos.create(PagoApi::class.java)
    }

    val comprobanteApi: ComprobanteApi by lazy{
        retrofitComprobantes.create(ComprobanteApi::class.java)
    }
}
