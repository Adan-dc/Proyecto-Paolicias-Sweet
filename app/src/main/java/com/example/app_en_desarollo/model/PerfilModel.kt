package com.example.app_en_desarollo.model

data class PerfilModel(
    val nombre: String = "",
    val empresa: String? = null,
    val telefono: String = "",
    val email: String = "",
    val cargando: Boolean = true
)