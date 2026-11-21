package com.example.app_en_desarollo.model

data class ClienteModel(
    val nombre: String,
    val telefono: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class EmpresaResponse(
    val id: Long,
    val rut: String?,
    val razonSocial: String?,
    val telefono: String?
)

data class ClienteResponse(
    val id: Long,
    val nombre: String?,
    val telefono: String?,
    val email: String?,
    val rol: String?,
    val empresa: EmpresaResponse?
)

data class EmpresaModel(
    val rut: String,
    val razonSocial: String,
    val telefono: String,
    val password: String
)

data class EmpresaLoginRequest(
    val rut: String,
    val password: String
)