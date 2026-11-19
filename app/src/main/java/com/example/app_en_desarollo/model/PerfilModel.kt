package com.example.app_en_desarollo.model

data class PerfilModel(
    val tipoUsuario: TipoUsuario = TipoUsuario.CLIENTE,

    //cliente
    val nombre: String = "",
    val email: String = "",

    //empresa
    val razonSocial: String = "",
    val rut: String = "",

    //comun
    val telefono: String = "",

    val cargando: Boolean = true
)