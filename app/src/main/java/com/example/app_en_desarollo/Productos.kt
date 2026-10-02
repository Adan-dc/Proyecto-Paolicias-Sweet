package com.example.app_en_desarollo

abstract class Producto (
    var Nombre: String,
    var Foto: String,
    var Descripcion: String,
    var PrecioUnitario: Double) {
    //abstract fun listaIngredientes(cantidad: Int): String//Aca van las funciones comunes Abstract

    fun calcularTotalAPagarProductos(cantidad: Int): Double {
        return PrecioUnitario * cantidad
    }
}
// tipos de productos
class TortaTresLeches(
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

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
class TortaChocolate (
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

}

class TortaRedVelvet (
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

}

class TortaSelvaNegra (
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

}

class CanapeAvePimenton (
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

}

class CanapeSalmon (
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

}

class CanapeVegetariano (
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

}

class MiniEmpanadas (
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

}

class Tapaditos (
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

}

class Postres (
    Nombre: String,
    Foto: String,
    Descripcion: String,
    PrecioUnitario: Double
) : Producto(Nombre, Foto, Descripcion, PrecioUnitario){

}
