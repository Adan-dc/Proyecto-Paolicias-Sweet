package com.example.app_en_desarollo.model

data class ReservaGuardada(
    val id: Int,
    val tipoEvento: String,
    val fecha: String,
    val hora: String,
    val asistentes: Int,
    val tieneObservaciones: Boolean
)