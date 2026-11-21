package com.example.app_en_desarollo.model

data class TrabajadorModel (
    val id: Long? = null,
    val nombreCompleto: String = "",
    val email: String = "",
    val rut: String = "",
    val direccion: String = "",
    val estado: Boolean = false,
    val idReservaAsignada: Long? = null,

    val nombreError: String? = null,
    val emailError: String? = null,
    val rutError: String? = null,
    val direccionError: String? = null,
    val errorMessage: String? = null,
)