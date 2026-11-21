package com.example.app_en_desarollo.model

data class CategoriaResponse(
    val id: Long,
    val nombre: String,
    val seccion: String
)

data class IngredienteResponse(
    val id: Long,
    val nombre: String,
    val precioUnidad: Double? = null,
    val unidadMedida: String? = null
)

data class ProductoIngredienteResponse(
    val id: Long,
    val ingrediente: IngredienteResponse,
    val cantidad: Double
)

data class ProductoResponse(
    val id: Long,
    val nombre: String,
    val precioBase: Int,
    val imagenUrl: String?,
    val descripcion: String?,
    val categoria: CategoriaResponse?,
    val ingredientes: List<ProductoIngredienteResponse>?
)