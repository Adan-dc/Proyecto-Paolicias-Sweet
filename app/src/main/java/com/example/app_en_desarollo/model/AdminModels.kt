package com.example.app_en_desarollo.model

data class CategoriaRef(
    val id: Long
)

data class ProductoRequest(
    val nombre: String,
    val precioBase: Int,
    val imagenUrl: String?,
    val descripcion: String?,
    val categoria: CategoriaRef
)

data class IngredienteRequest(
    val nombre: String,
    val precioUnidad: Double,
    val unidadMedida: String
)

data class PackRequest(
    val nombre: String,
    val precio: Int,
    val imagenUrl: String?,
    val tipo: String?,
    val personas: Int?,
    val activo: Boolean
)

data class ImagenResponse(
    val nombre: String
)