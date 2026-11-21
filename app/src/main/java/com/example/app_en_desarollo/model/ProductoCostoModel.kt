package com.example.app_en_desarollo.model

data class ProductoCostoModel(
    val productoId: Long,
    val nombreProducto: String,
    val cantidad: Int,
    val ingredientes: List<IngredienteCostoModel>,
    val costoTotal: Double
)
