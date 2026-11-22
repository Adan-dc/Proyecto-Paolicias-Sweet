package com.example.app_en_desarollo.data.api

data class CrearReservaRequest(
    val usuarioEmail: String,
    val tipoEvento: String,
    val fecha: String,
    val hora: String,
    val asistentes: Int,
    val tieneObservaciones: Boolean,
    val observaciones: String,
    val packs: List<PackSeleccionado>,
    val productos: List<ProductoSeleccionado>
)

data class PackSeleccionado(
    val packId: Long,
    val cantidad: Int
)

data class ProductoSeleccionado(
    val productoId: Long,
    val cantidad: Int
)
