package com.example.app_en_desarollo.ui.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.gabriel.paoliciasSweet.model.ProductoReservaModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_en_desarollo.R
import com.gabriel.paoliciasSweet.model.ProductosModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.model.ProductoResponse
import com.example.app_en_desarollo.network.RetrofitClient
import kotlinx.coroutines.launch
import com.example.app_en_desarollo.model.PackResponse
class CatalogoViewModel : ViewModel(){
    var productos by mutableStateOf<List<ProductosModel>>(emptyList())
        private set

    var cargando by mutableStateOf(true)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            cargando = true
            try {
                val lista = RetrofitClient.productoApi.listar()
                val productosCargados = lista.map { aModelo(it) }
                val packsCargados = try {
                    RetrofitClient.packApi.listar()
                        .filter { it.activo != false }
                        .map { packAModelo(it) }
                } catch (e: Exception) {
                    Log.e("Catalogo", "Error cargando packs", e)
                    emptyList()
                }
                productos = productosCargados + packsCargados
                error = null
            } catch (e: Exception) {
                Log.e("Catalogo", "Error cargando productos", e)
                error = "No se pudieron cargar los productos"
            }
            cargando = false
        }
    }

    private fun aModelo(p: ProductoResponse): ProductosModel {
        val ingredientes = p.ingredientes.orEmpty()
            .joinToString("\n") { it.ingrediente.nombre }

        return ProductosModel(
            nombre = p.nombre,
            tipo = p.categoria?.nombre ?: "",
            precio = p.precioBase,
            ingredientes = ingredientes,
            imagen = imagenPorNombre(p.imagenUrl),
            imagenUrl = p.imagenUrl,
            seccion = p.categoria?.seccion ?: "",
            descripcion = p.descripcion ?: ""
        )
    }
    private fun packAModelo(p: PackResponse): ProductosModel {
        val contenido = p.productos.orEmpty()
            .groupBy { it.grupo ?: "Incluye" }
            .map { (grupo, items) ->
                grupo + ":\n" + items.joinToString("\n") {
                    "- " + (it.producto?.nombre ?: "") + " x" + it.cantidad
                }
            }
            .joinToString("\n\n")

        return ProductosModel(
            nombre = p.nombre,
            tipo = p.tipo ?: "",
            precio = p.precio,
            ingredientes = contenido,
            imagen = when (p.imagenUrl) {
                "packestandar" -> R.drawable.packestandar
                "packpremium" -> R.drawable.packpremium
                "packvegetariano" -> R.drawable.packvegetariano
                "promocumpleanos" -> R.drawable.promocumpleanos
                else -> R.drawable.imagenmenu
            },
            imagenUrl = p.imagenUrl,
            seccion = "Packs",
            descripcion = p.personas?.let { "$it personas" } ?: ""
        )
    }
    private fun imagenPorNombre(nombre: String?): Int {
        return when (nombre?.substringBeforeLast(".")) {
            "tartadefresasyvainilla" -> R.drawable.tartadefresasyvainilla
            "cookiesdechocolate" -> R.drawable.cookiesdechocolate
            "bizcochodelimon" -> R.drawable.bizcochodelimon
            "browniedechocolate" -> R.drawable.browniedechocolate
            "cheesecakedeframbuesa" -> R.drawable.cheesecakedeframbuesa
            "rollitosdecanela" -> R.drawable.rollitosdecanela
            "ensaladamixdeverduras" -> R.drawable.ensaladamixdeverduras
            "ensaladacesarconpollo" -> R.drawable.ensaladacesarconpollo
            "ensaladachilena" -> R.drawable.ensaladachilena
            "ensaladadetomateconzanahorias" -> R.drawable.ensaladadetomateconzanahorias
            "ensaladagourmetcontrufas" -> R.drawable.ensaladagourmetcontrufas
            "ensaladadecaviar" -> R.drawable.ensaladadecaviar
            "bruschettasdetomate" -> R.drawable.bruschettasdetomate
            "polloalhornoconpapas" -> R.drawable.polloalhornoconpapas
            "limonadaconmenta" -> R.drawable.limonadaconmenta
            "jugodefrutilla" -> R.drawable.jugodefrutilla
            "jugodenaranja" -> R.drawable.jugodenaranja
            "cafelatte" -> R.drawable.cafelatte
            else -> R.drawable.emblema_paolicias_sweet
        }
    }
    var seccionSeleccionada by mutableStateOf("Packs")
    var categoriaSeleccionada by mutableStateOf("Todas")

    fun cambiarSeccion(seccion: String) {
        seccionSeleccionada = seccion
        categoriaSeleccionada = "Todas"
    }

    fun cambiarCategoria(categoria: String) {
        categoriaSeleccionada = categoria
    }
    private fun normalizar(texto: String): String {
        return java.text.Normalizer.normalize(texto.trim(), java.text.Normalizer.Form.NFD)
            .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
            .lowercase()
    }
    fun obtenerProductosFiltrados(): List<ProductosModel> {
        val filtrados = mutableListOf<ProductosModel>()
        val filtro = normalizar(categoriaSeleccionada)

        for (producto in productos) {
            if (producto.seccion == seccionSeleccionada &&
                (categoriaSeleccionada == "Todas" ||
                        normalizar(producto.tipo).startsWith(filtro))
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

    val totalProductos: Int
        get() = productosReserva.sumOf { it.producto.precio * it.cantidad }
}
