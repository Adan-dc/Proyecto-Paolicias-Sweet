package com.example.app_en_desarollo.ui.viewmodel
import androidx.compose.runtime.mutableStateListOf
import com.gabriel.paoliciasSweet.model.ProductoReservaModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_en_desarollo.R
import com.gabriel.paoliciasSweet.model.ProductosModel

class CatalogoViewModel : ViewModel(){
    val productos = listOf(
        ProductosModel("Tarta de fresas y vainilla", "Tarta", 5000, "Ingredientes orientativos: \n" +
                "Fresas \n" +
                "Vainilla\n" +
                "Harina\n" +
                "Azúcar \n" +
                "Huevo\n" +
                "Mantequilla\n" +
                "Crema.", R.drawable.tartadefresasyvainilla, "Reposteria"),
        ProductosModel("Cookies de chocolate", "Galleta", 3000, "Ingredientes orientativos: \n" +
                "Harina \n" +
                "Mantequilla\n" +
                "Azucar\n" +
                "Huevo \n" +
                "Cacao\n" +
                "Esencia de vainilla\n" +
                "Sal.", R.drawable.cookiesdechocolate, "Reposteria"),
        ProductosModel("Bizcocho de limón", "Bizcocho", 5000, "Ingredientes orientativos: \n" +
                "Azucar \n" +
                "Harina\n" +
                "Polvo de hornear\n" +
                "Maicena\n" +
                "Aceite de girasol\n" +
                "Jugo de limon\n" +
                "Huevo\n" +
                "Leche\n" +
                "Azucar glas.", R.drawable.bizcochodelimon, "Reposteria"),
        ProductosModel("Brownies de chocolate", "Brownie", 2500, "Ingredientes orientativos: \n" +
                "Chocolate negro \n" +
                "Mantequilla sin sal\n" +
                "Huevo\n" +
                "Azucar\n" +
                "Harina\n" +
                "Esencia de vainilla\n" +
                "Sal.", R.drawable.browniedechocolate, "Reposteria"),
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
                "Maicena.", R.drawable.cheesecakedeframbuesa, "Reposteria"),
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
                "Canela molida.", R.drawable.rollitosdecanela, "Reposteria"),
        ProductosModel(nombre = "Ensalada mix de verduras", tipo = "Ensalada", precio = 1500, ingredientes = "Ingredientes orientativos:\n" +
                "Lechuga\n" +
                "Tomate cherry\n" +
                "Pepino\n" +
                "Zanahoria\n" +
                "Garbanzos\n" +
                "Aceite de oliva\n" +
                "Sal.",
            imagen = R.drawable.ensaladamixdeverduras, seccion = "Menu"),

        ProductosModel(nombre = "Ensalada cesar con pollo", tipo = "Ensalada", precio = 2000, ingredientes = "Ingredientes orientativos:\n" +
                "Lechuga\n" +
                "Pollo\n" +
                "Crutones\n" +
                "Queso parmesano\n" +
                "Salsa César.",
            imagen = R.drawable.ensaladacesarconpollo, seccion = "Menu"),

        ProductosModel(nombre = "Ensalada chilena", tipo = "Ensalada", precio = 1250, ingredientes = "Ingredientes orientativos:\n" +
                "Tomate\n" +
                "Cebolla\n" +
                "Cilantro\n" +
                "Aceite\n" +
                "Sal.",
            imagen = R.drawable.ensaladachilena, seccion = "Menu"),

        ProductosModel(nombre = "Ensalada de tomate con zanahorias", tipo = "Ensalada", precio = 1290, ingredientes = "Ingredientes orientativos:\n" +
                "Tomate\n" +
                "Zanahoria\n" +
                "Cilantro\n" +
                "Aceite de oliva\n" +
                "Sal.",
            imagen = R.drawable.ensaladadetomateconzanahorias, seccion = "Menu"),

        ProductosModel(nombre = "Ensalada Gourmet con trufas", tipo = "Ensalada", precio = 2460, ingredientes = "Ingredientes orientativos:\n" +
                "Mix de hojas verdes\n" +
                "Champiñones\n" +
                "Queso parmesano\n" +
                "Trufa\n" +
                "Aceite de oliva\n" +
                "Sal.",
            imagen = R.drawable.ensaladagourmetcontrufas, seccion = "Menu"),

        ProductosModel(nombre = "Ensalada de caviar", tipo = "Ensalada", precio = 3690, ingredientes = "Ingredientes orientativos:\n" +
                "Mix de hojas verdes\n" +
                "Caviar\n" +
                "Tomate cherry\n" +
                "Pepino\n" +
                "Huevo\n" +
                "Aceite de oliva.",
            imagen = R.drawable.ensaladadecaviar, seccion = "Menu"),
        ProductosModel(nombre = "Bruschettas de tomate y albahaca", tipo = "Entrada", precio = 2500, ingredientes = "Ingredientes orientativos:\n" +
                "Pan baguette\n" +
                "Tomate\n" +
                "Albahaca\n" +
                "Ajo\n" +
                "Aceite de oliva\n" +
                "Sal.",
            imagen = R.drawable.bruschettasdetomate, seccion = "Menu"),

        ProductosModel(nombre = "Pollo al horno con papas", tipo = "Plato de fondo", precio = 6500, ingredientes = "Ingredientes orientativos:\n" +
                "Pollo\n" +
                "Papas\n" +
                "Ajo\n" +
                "Romero\n" +
                "Aceite de oliva\n" +
                "Sal\n" +
                "Pimienta.",
            imagen = R.drawable.polloalhornoconpapas, seccion = "Menu")
    )
    var seccionSeleccionada by mutableStateOf("Reposteria")
    var categoriaSeleccionada by mutableStateOf("Todas")

    fun cambiarSeccion(seccion: String) {
        seccionSeleccionada = seccion
        categoriaSeleccionada = "Todas"
    }

    fun cambiarCategoria(categoria: String) {
        categoriaSeleccionada = categoria
    }
    fun obtenerProductosFiltrados(): List<ProductosModel> {
        val filtrados = mutableListOf<ProductosModel>()

        for (producto in productos) {
            if (producto.seccion == seccionSeleccionada &&
                (categoriaSeleccionada == "Todas" ||
                        producto.tipo == categoriaSeleccionada)
            ) {
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
