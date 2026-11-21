package com.example.app_en_desarollo.model

data class PackProductoResponse(
    val id: Long,
    val producto: ProductoResponse?,
    val cantidad: Int,
    val grupo: String?
)

data class PackResponse(
    val id: Long,
    val nombre: String,
    val precio: Int,
    val imagenUrl: String?,
    val tipo: String?,
    val personas: Int?,
    val activo: Boolean?,
    val productos: List<PackProductoResponse>?
)