package com.example.app_en_desarollo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.model.CategoriaRef
import com.example.app_en_desarollo.model.CategoriaResponse
import com.example.app_en_desarollo.model.IngredienteResponse
import com.example.app_en_desarollo.model.ProductoRequest
import com.example.app_en_desarollo.model.ProductoResponse
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.network.subirImagen
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class IngredienteSeleccionado(
    val id: Long,
    val nombre: String,
    val unidad: String?,
    val cantidad: Double
)

class AdminProductosViewModel : ViewModel() {

    var productos by mutableStateOf<List<ProductoResponse>>(emptyList())
        private set

    var categorias by mutableStateOf<List<CategoriaResponse>>(emptyList())
        private set

    var ingredientesDisponibles by mutableStateOf<List<IngredienteResponse>>(emptyList())
        private set

    var cargando by mutableStateOf(false)
        private set

    var mensaje by mutableStateOf<String?>(null)
        private set

    fun limpiarMensaje() {
        mensaje = null
    }

    fun cargar() {
        viewModelScope.launch {
            cargando = true
            try {
                productos = RetrofitGeneral.productoApi.listar()
                categorias = RetrofitGeneral.categoriaApi.listar()
            } catch (e: Exception) {
                mensaje = "No se pudieron cargar los productos"
            }
            try {
                ingredientesDisponibles = RetrofitGeneral.ingredienteApi.listar()
            } catch (e: Exception) {
                mensaje = "No se pudieron cargar los ingredientes"
            }
            cargando = false
        }
    }

    fun guardar(
        id: Long?,
        nombre: String,
        precioBase: Int,
        descripcion: String,
        imagenUrl: String?,
        imagenBytes: ByteArray?,
        categoriaId: Long,
        ingredientes: List<IngredienteSeleccionado>,
        productoOriginal: ProductoResponse?,
        onListo: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val imagenFinal = if (imagenBytes != null) subirImagen(imagenBytes) else imagenUrl

                val request = ProductoRequest(
                    nombre = nombre,
                    precioBase = precioBase,
                    imagenUrl = imagenFinal,
                    descripcion = descripcion.ifBlank { null },
                    categoria = CategoriaRef(categoriaId)
                )

                val guardado = if (id == null) {
                    RetrofitGeneral.productoApi.crear(request)
                } else {
                    RetrofitGeneral.productoApi.actualizar(id, request)
                }

                val idsOriginales = productoOriginal?.ingredientes
                    ?.map { it.ingrediente.id } ?: emptyList()
                val idsNuevos = ingredientes.map { it.id }

                idsOriginales
                    .filter { it !in idsNuevos }
                    .forEach { RetrofitGeneral.productoApi.quitarIngrediente(guardado.id, it) }

                ingredientes.forEach {
                    RetrofitGeneral.productoApi.agregarIngrediente(guardado.id, it.id, it.cantidad)
                }

                productos = RetrofitGeneral.productoApi.listar()
                onListo()
            } catch (e: Exception) {
                mensaje = "No se pudo guardar el producto"
            }
        }
    }

    fun eliminar(id: Long) {
        viewModelScope.launch {
            try {
                RetrofitGeneral.productoApi.eliminar(id)
                productos = RetrofitGeneral.productoApi.listar()
            } catch (e: HttpException) {
                mensaje = if (e.code() == 409) {
                    "No se puede eliminar, el producto esta en un pack"
                } else {
                    "No se pudo eliminar el producto"
                }
            } catch (e: Exception) {
                mensaje = "No se pudo conectar con el servidor"
            }
        }
    }
}