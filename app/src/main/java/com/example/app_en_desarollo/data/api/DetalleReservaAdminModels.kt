package com.example.app_en_desarollo.data.api

data class ReservaProductoAdmin(
    val id: Long,
    val reservaId: Long,
    val productoId: Long,
    val nombreProducto: String?,
    val cantidad: Int,
    val grupo: String?
)

data class ReservaPackAdmin(
    val id: Long,
    val reservaId: Long,
    val packId: Long,
    val nombrePack: String?,
    val cantidad: Int,
    val precioUnitario: Int,
    val subtotal: Int
)
