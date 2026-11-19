package com.example.app_en_desarollo.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGeneral {

    private const val BASE_URL = "https://api.paoliciassweet.cl/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val trabajadorApi: TrabajadorApi by lazy {
        retrofit.create(TrabajadorApi::class.java)
    }
}