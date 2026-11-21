package com.example.app_en_desarollo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val URL_PRODUCTOS = "http://localhost:8085/"
    private const val URL_CLIENTES = "http://localhost:8086/"

    private fun crear(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitProductos by lazy { crear(URL_PRODUCTOS) }
    private val retrofitClientes by lazy { crear(URL_CLIENTES) }

    fun urlImagen(nombre: String): String {
        return URL_PRODUCTOS + "api/v1/imagen/" + nombre.removePrefix("imagen-")
    }

    val productoApi: ProductoApi by lazy {
        retrofitProductos.create(ProductoApi::class.java)
    }

    val packApi: PackApi by lazy {
        retrofitProductos.create(PackApi::class.java)
    }

    val ingredienteApi: IngredienteApi by lazy {
        retrofitProductos.create(IngredienteApi::class.java)
    }

    val categoriaApi: CategoriaApi by lazy {
        retrofitProductos.create(CategoriaApi::class.java)
    }

    val imagenApi: ImagenApi by lazy {
        retrofitProductos.create(ImagenApi::class.java)
    }

    val clienteApi: ClienteApi by lazy {
        retrofitClientes.create(ClienteApi::class.java)
    }

    val empresaApi: EmpresaApi by lazy {
        retrofitClientes.create(EmpresaApi::class.java)
    }
}