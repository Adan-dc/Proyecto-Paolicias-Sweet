package com.example.app_en_desarollo.model

data class CostoCalculoModel(
    val id: Long? = null,
    val idReserva: Long,
    val cantidadProductosTotal: Int,
    val costoTotal: Double
)
