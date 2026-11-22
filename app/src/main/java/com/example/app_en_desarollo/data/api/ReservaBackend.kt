package com.example.app_en_desarollo.data.api

data class ReservaBackend(
    val id: Int,
    val usuarioEmail: String?,
    val tipoEvento: String?,
    val fecha: String?,
    val hora: String?,
    val asistentes: Int,
    val tieneObservaciones: Boolean,
    val estadoReserva: String?,
    val estadoAbono: String?,
    val presupuestoTotal: Int,
    val cantidadProductosTotal: Int = 0
)
