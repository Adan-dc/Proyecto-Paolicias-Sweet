package com.example.app_en_desarollo

class Producto (
    var Nombre: String,
    var Foto: String,
    var Descripcion: String,
    var PrecioUnitario: Double,
    val Tipo: String) {
    //abstract fun listaIngredientes(cantidad: Int): String//Aca van las funciones comunes Abstract

    fun calcularTotalAPagarProductos(cantidad: Int): Double {
        return PrecioUnitario * cantidad
    }
}
/*
// tipos de productos
class Torta(
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double,
    Tipo: String
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario, Tipo){

    /*override fun listaIngredientes(cantidad: Int): String {
        val harina = 1.5 * cantidad
        val huevos = 5 * cantidad
        val azucar = 1 * cantidad
        val polvoDeHornar = 1 * cantidad
        val esenciaDeVainilla = 1 * cantidad
        val lecheCondensada = 1 * cantidad
        val lecheEvaporada = 1 * cantidad
        //retornara la lista que necesita comprar la dueña para no hacer los calculos manual de lo que debe comprar
        return "Lista de ingredientes: " +
                "Harina (Tazas): $harina " +
                "Huevos: $huevos" +
                "Azucar (Tazas): $azucar" +
                "Polvo de hornear (Cucharadita): $polvoDeHornar " +
                "Esencia de vainilla (Cucharadita): $esenciaDeVainilla " +
                "Leche condensada (Tarro): $lecheCondensada " +
                "Leche evaporada (Tarro): $lecheEvaporada "
    }
    */
}
 */
