package com.example.app_en_desarollo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.model.PackRequest
import com.example.app_en_desarollo.model.PackResponse
import com.example.app_en_desarollo.model.ProductoResponse
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.network.subirImagen
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class ProductoSeleccionado(
    val id: Long,
    val nombre: String,
    val cantidad: Int,
    val grupo: String
)

class AdminPacksViewModel : ViewModel() {

    var packs by mutableStateOf<List<PackResponse>>(emptyList())
        private set

    var productosDisponibles by mutableStateOf<List<ProductoResponse>>(emptyList())
        private set

    var mensaje by mutableStateOf<String?>(null)
        private set

    fun limpiarMensaje() {
        mensaje = null
    }

    private fun imagenPorTipo(tipo: String?): String? {
        return when (tipo) {
            "ESTANDAR" -> "packestandar"
            "PREMIUM" -> "packpremium"
            "VEGETARIANO" -> "packvegetariano"
            "PROMO" -> "promocumpleanos"
            else -> null
        }
    }

    fun cargar() {
        viewModelScope.launch {
            try {
                packs = RetrofitGeneral.packApi.listar()
            } catch (e: Exception) {
                mensaje = "No se pudieron cargar los packs"
            }
            try {
                productosDisponibles = RetrofitGeneral.productoApi.listar()
            } catch (e: Exception) {
                mensaje = "No se pudieron cargar los productos"
            }
        }
    }

    fun guardar(
        id: Long?,
        nombre: String,
        precio: Int,
        tipo: String,
        personas: Int?,
        activo: Boolean,
        imagenActual: String?,
        imagenBytes: ByteArray?,
        productos: List<ProductoSeleccionado>,
        packOriginal: PackResponse?,
        onListo: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val imagenFinal: String? = if (imagenBytes != null) {
                    subirImagen(imagenBytes)
                } else if (imagenActual != null && imagenActual.startsWith("imagen-")) {
                    imagenActual
                } else {
                    imagenPorTipo(tipo) ?: imagenActual
                }

                val request = PackRequest(
                    nombre = nombre,
                    precio = precio,
                    imagenUrl = imagenFinal,
                    tipo = tipo,
                    personas = personas,
                    activo = activo
                )

                val guardado = if (id == null) {
                    RetrofitGeneral.packApi.crear(request)
                } else {
                    RetrofitGeneral.packApi.actualizar(id, request)
                }

                val idsOriginales: List<Long> = packOriginal?.productos
                    ?.mapNotNull { it.producto?.id } ?: emptyList()
                val idsNuevos: List<Long> = productos.map { it.id }

                idsOriginales
                    .filter { it !in idsNuevos }
                    .forEach { RetrofitGeneral.packApi.quitarProducto(guardado.id, it) }

                productos.forEach {
                    RetrofitGeneral.packApi.agregarProducto(guardado.id, it.id, it.cantidad, it.grupo)
                }

                packs = RetrofitGeneral.packApi.listar()
                onListo()
            } catch (e: Exception) {
                mensaje = "No se pudo guardar el pack"
            }
        }
    }

    fun cambiarActivo(pack: PackResponse, activo: Boolean) {
        viewModelScope.launch {
            try {
                val request = PackRequest(
                    nombre = pack.nombre,
                    precio = pack.precio,
                    imagenUrl = pack.imagenUrl,
                    tipo = pack.tipo,
                    personas = pack.personas,
                    activo = activo
                )
                RetrofitGeneral.packApi.actualizar(pack.id, request)
                packs = RetrofitGeneral.packApi.listar()
            } catch (e: Exception) {
                mensaje = "No se pudo cambiar el estado del pack"
            }
        }
    }

    fun eliminar(id: Long) {
        viewModelScope.launch {
            try {
                RetrofitGeneral.packApi.eliminar(id)
                packs = RetrofitGeneral.packApi.listar()
            } catch (e: HttpException) {
                mensaje = "No se pudo eliminar el pack"
            } catch (e: Exception) {
                mensaje = "No se pudo conectar con el servidor"
            }
        }
    }
}