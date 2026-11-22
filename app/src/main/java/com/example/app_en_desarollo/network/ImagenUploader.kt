package com.example.app_en_desarollo.network

import com.example.app_en_desarollo.data.api.RetrofitGeneral
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

suspend fun subirImagen(bytes: ByteArray): String {
    val cuerpo = bytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
    val parte = MultipartBody.Part.createFormData("archivo", "foto.jpg", cuerpo)
    return RetrofitGeneral.imagenApi.subir(parte).nombre
}