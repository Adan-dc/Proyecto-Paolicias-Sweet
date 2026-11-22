package com.example.app_en_desarollo.data.api

import com.example.app_en_desarollo.network.CategoriaApi
import com.example.app_en_desarollo.network.ClienteApi
import com.example.app_en_desarollo.network.EmpresaApi
import com.example.app_en_desarollo.network.ImagenApi
import com.example.app_en_desarollo.network.IngredienteApi
import com.example.app_en_desarollo.network.PackApi
import com.example.app_en_desarollo.network.ProductoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGeneral {

    private const val URL_BASE = "https://server.greengea.cl/paolicias/"
    private const val URL_PRODUCTOS = URL_BASE + "producto/"
    private const val URL_CLIENTES = URL_BASE + "cliente/"
    private const val URL_TRABAJADORES = URL_BASE + "trabajadores/"
    private const val URL_RESERVAS = URL_BASE + "reservas/"
    private const val URL_COSTO = URL_BASE + "costo/"
    private const val URL_PAGOS = URL_BASE + "pago/"
    private const val URL_COMPROBANTES = URL_BASE + "comprobante/"

    private fun crear(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitProductos by lazy { crear(URL_PRODUCTOS) }
    private val retrofitClientes by lazy { crear(URL_CLIENTES) }
    private val retrofitTrabajadores by lazy { crear(URL_TRABAJADORES) }
    private val retrofitReservas by lazy { crear(URL_RESERVAS) }
    private val retrofitCosto by lazy { crear(URL_COSTO) }
    private val retrofitPagos by lazy { crear(URL_PAGOS) }
    private val retrofitComprobantes by lazy { crear(URL_COMPROBANTES) }

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

    val trabajadorApi: TrabajadorApi by lazy {
        retrofitTrabajadores.create(TrabajadorApi::class.java)
    }

    val reservaAdminApi: ReservaAdminApi by lazy {
        retrofitReservas.create(ReservaAdminApi::class.java)
    }

    val costoApi: CostoApi by lazy {
        retrofitCosto.create(CostoApi::class.java)
    }

    val reservaApi: ReservaApi by lazy {
        retrofitReservas.create(ReservaApi::class.java)
    }

    val pagoApi: PagoApi by lazy {
        retrofitPagos.create(PagoApi::class.java)
    }

    val comprobanteApi: ComprobanteApi by lazy {
        retrofitComprobantes.create(ComprobanteApi::class.java)
    }
}
