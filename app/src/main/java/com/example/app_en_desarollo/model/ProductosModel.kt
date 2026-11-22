package com.gabriel.paoliciasSweet.model

data class ProductosModel (
    val id: Long = 0,
    val nombre: String = "",
    val tipo: String = "",
    val precio: Int = 0,
    val ingredientes: String = "",
    val imagen: Int = 0,
    val seccion: String = "",
    val descripcion: String = "",
    val imagenUrl: String? = null
)