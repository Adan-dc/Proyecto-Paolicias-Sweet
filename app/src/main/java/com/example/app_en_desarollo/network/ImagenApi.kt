package com.example.app_en_desarollo.network

import com.example.app_en_desarollo.model.ImagenResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImagenApi {

    @Multipart
    @POST("api/v1/imagen")
    suspend fun subir(@Part archivo: MultipartBody.Part): ImagenResponse
}