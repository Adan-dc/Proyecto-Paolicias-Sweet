package com.example.app_en_desarollo.data.api

data class CrearPagoRequest(
    val reservaId: Long,
    val monto: Int,
    val tipoPago: String = "ABONO",
    val metodoPago: String = "TRANSFERENCIA"
)

data class PagoBackend(
    val id: Long,
    val reservaId: Long,
    val monto: Int,
    val tipoPago: String,
    val metodoPago: String,
    val estadoPago: String,
    val fechaPago: String?
)
