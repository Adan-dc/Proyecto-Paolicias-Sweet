package com.matias.paoliciassweet.ui.viewmodel
import androidx.compose.runtime.mutableStateListOf
import com.matias.paoliciassweet.model.ProductoReservaModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.matias.paoliciassweet.R
import com.matias.paoliciassweet.model.ProductosModel

class CatalogoViewModel : ViewModel(){
    val productos = listOf(
        ProductosModel("Tarta de fresas y vainilla", "Tarta", 5000, "Ingredientes orientativos: \n" +
                "Fresas \n" +
                "Vainilla\n" +
                "Harina\n" +
                "Azúcar \n" +
                "Huevo\n" +
                "Mantequilla\n" +
                "Crema.", R.drawable.tartadefresasyvainilla),
        ProductosModel("Cookies de chocolate", "Galleta", 3000, "Ingredientes orientativos: \n" +
                "Harina \n" +
                "Mantequilla\n" +
                "Azucar\n" +
                "Huevo \n" +
                "Cacao\n" +
                "Esencia de vainilla\n" +
                "Sal.", R.drawable.cookiesdechocolate),
        ProductosModel("Bizcocho de limón", "Bizcocho", 5000, "Ingredientes orientativos: \n" +
                "Azucar \n" +
                "Harina\n" +
                "Polvo de hornear\n" +
                "Maicena\n" +
                "Aceite de girasol\n" +
                "Jugo de limon\n" +
                "Huevo\n" +
                "Leche\n" +
                "Azucar glas.", R.drawable.bizcochodelimon),
        ProductosModel("Brownies de chocolate", "Brownie", 2500, "Ingredientes orientativos: \n" +
                "Chocolate negro \n" +
                "Mantequilla sin sal\n" +
                "Huevo\n" +
                "Azucar\n" +
                "Harina\n" +
                "Esencia de vainilla\n" +
                "Sal.", R.drawable.browniedechocolate),
        ProductosModel("Cheesecake de frambuesa", "Tarta", 4500, "Ingredientes orientativos: \n" +
                "Galletas\n" +
                "Mantequilla\n" +
                "Azucar\n" +
                "Queso crema\n" +
                "Azucar flor\n" +
                "Crema para batir\n" +
                "Jugo de limon\n" +
                "Ralladura de limon\n" +
                "Frambuesa\n" +
                "Maicena.", R.drawable.cheesecakedeframbuesa),
        ProductosModel("Rollitos de canela", "Bolleria", 3500, "Ingredientes orientativos: \n" +
                "Harina\n" +
                "Leche\n" +
                "Levadura\n" +
                "Azúcar\n" +
                "Huevo\n" +
                "Mantequilla\n" +
                "Sal\n" +
                "Vainilla\n" +
                "Azúcar rubia\n" +
                "Canela molida.", R.drawable.rollitosdecanela)
    )
    var categoriaSeleccionada by mutableStateOf("Todas")

    fun cambiarCategoria(categoria: String) {
        categoriaSeleccionada = categoria
    }
    fun obtenerProductosFiltrados(): List<ProductosModel> {
        val filtrados = mutableListOf<ProductosModel>()

        for (producto in productos) {
            if (categoriaSeleccionada == "Todas" ||
                producto.tipo == categoriaSeleccionada) {
                filtrados.add(producto)
            }
        }

        return filtrados
    }

    fun buscarProducto(nombre: String): ProductosModel? {
        for (producto in productos) {
            if (producto.nombre == nombre) {
                return producto
            }
        }

        return null
    }

    val productosReserva = mutableStateListOf<ProductoReservaModel>()

    val totalProductos: Int
        get() = productosReserva.sumOf { it.producto.precio * it.cantidad }

    fun agregarProducto(producto: ProductosModel, cantidad: Int) {
        if (cantidad < 1) {
            return
        }

        for (posicion in productosReserva.indices) {
            if (productosReserva[posicion].producto.nombre == producto.nombre) {
                productosReserva[posicion] =
                    ProductoReservaModel(producto, cantidad)
                return
            }
        }

        productosReserva.add(ProductoReservaModel(producto, cantidad))
    }
}
