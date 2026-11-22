package com.example.app_en_desarollo.ui.navigation


import android.net.Uri
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import android.widget.Toast
import com.example.app_en_desarollo.data.preferences.SesionBiometrica
import com.example.app_en_desarollo.ui.components.FooterAdmin
import com.example.app_en_desarollo.ui.components.LocalNavegarAdmin
import androidx.compose.runtime.CompositionLocalProvider
import com.example.app_en_desarollo.ui.screen.AdminProductosScreen
import com.example.app_en_desarollo.ui.screen.CrearTrabajadorScreen
import com.example.app_en_desarollo.ui.screen.DetalleCostoScreen
import com.example.app_en_desarollo.ui.screen.DetalleTrabajadorScreen
import com.example.app_en_desarollo.ui.screen.ModificarTrabajadorScreen
import com.example.app_en_desarollo.ui.screen.ResumenCostoScreen
import com.example.app_en_desarollo.ui.screen.ScreenCosto
import com.example.app_en_desarollo.ui.viewmodel.CostoViewModel
import com.example.app_en_desarollo.ui.viewmodel.TrabajadorViewModel
import com.example.app_en_desarollo.ui.screen.ComprobantesAdminScreen
import com.example.app_en_desarollo.ui.screen.DetalleReservaAdminScreen
import com.example.app_en_desarollo.ui.screen.CatalogoScreen
import com.example.app_en_desarollo.ui.screen.FormularioRegistroCliente
import com.example.app_en_desarollo.ui.screen.FormularioSesionCliente
import com.example.app_en_desarollo.ui.screen.InicioScreen
import com.example.app_en_desarollo.ui.screen.ObservacionesScreen
import com.example.app_en_desarollo.ui.screen.TransferenciaScreen
import com.example.app_en_desarollo.ui.screen.ReservaScreen
import com.example.app_en_desarollo.ui.screen.ReservasRealizadasScreen
import com.example.app_en_desarollo.ui.screen.ScreenTrabajador
import com.example.app_en_desarollo.ui.screen.infoPerfil
import com.example.app_en_desarollo.ui.screen.CalendarioAdminScreen
import com.example.app_en_desarollo.ui.viewmodel.CatalogoViewModel
import com.example.app_en_desarollo.ui.viewmodel.ReservaViewModel
import com.example.app_en_desarollo.ui.screen.ReservasAdminScreen
import com.gabriel.paoliciasSweet.ui.screen.ProductosScreen





object Routes{

    const val ADMIN_PRODUCTOS = "admin_productos"
    const val DETALLE_TRABAJADOR = "detalle_trabajador"
    const val CREAR_TRABAJADOR = "crear_trabajador"
    const val MODIFICAR_TRABAJADOR = "modificar_trabajador"
    const val COSTO = "costo"
    const val DETALLE_COSTO = "detalle_costo"
    const val RESUMEN_COSTO = "resumen_costo"

    const val CATEGORIA = "categoria"

    const val PRODUCTO = "producto/{nombre}"

    const val INICIO = "inicio"
    const val REGISTER = "register"
    const val PERFIL = "perfil"

    const val CATALOGO = "catalogo"

    const val RESERVA = "reserva"

    const val OBSERVACIONES = "observaciones"

    const val RESERVAS_REALIZADAS = "reservas realizadas"
    const val TRANSFERENCIA = "transferencia/{reservaId}"

    const val LOGIN = "login"

    const val ADMIN = "admin"

    const val RESERVAS_ADMIN = "reservas_admin"

    const val DETALLE_RESERVA_ADMIN = "detalle_reserva_admin/{reservaId}"

    const val COMPROBANTES_ADMIN = "comprobantes_admin/{comprobanteId}"

    const val CALENDARIO_ADMIN = "calendario_admin"


}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val context = LocalContext.current

    val catalogoViewModel: CatalogoViewModel = viewModel()
    val reservaViewModel: ReservaViewModel = viewModel()
    val trabajadorViewModel: TrabajadorViewModel = viewModel()
    val costoViewModel: CostoViewModel = viewModel()

    fun navegarFooter(ruta: String){
        navController.navigate(ruta){
            launchSingleTop = true
        }
    }

    fun navegarAdmin(seccion: String){
        when (seccion) {
            "Calendario" -> navegarFooter(Routes.CALENDARIO_ADMIN)
            "Productos" -> navegarFooter(Routes.ADMIN_PRODUCTOS)
            "Costo" -> navegarFooter(Routes.COSTO)
            "Reservas" -> navegarFooter(Routes.RESERVAS_ADMIN)
            "Personal" -> navegarFooter(Routes.ADMIN)
            "Perfil" -> navegarFooter(Routes.PERFIL)
            "Comprobantes" -> navController.navigate("comprobantes_admin/-1")
        }
    }

    CompositionLocalProvider(LocalNavegarAdmin provides { seccion -> navegarAdmin(seccion) }) {
        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier.fillMaxSize(),
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it / 3 }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it / 3 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
        ) {
            composable(Routes.LOGIN) {
                FormularioSesionCliente(
                    onNavigateToMenu = {

                        reservaViewModel.setUsuario(
                            SesionBiometrica.obtenerIdentificador(context) ?: ""
                        )
                        navController.navigate(Routes.INICIO) {
                            popUpTo(Routes.LOGIN) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToRegister = {
                        navegarFooter(Routes.REGISTER)
                    },
                    onNavigateToCatalogo = {
                        navegarFooter(Routes.CATALOGO)
                    },
                    onNavigateToReserva = {
                        navegarFooter(Routes.RESERVA)
                    },
                    onNavigateToPerfil = {
                        navegarFooter(Routes.PERFIL)
                    },
                    onNavigateToAdmin = {
                        navegarFooter(Routes.CALENDARIO_ADMIN)
                    }
                )
            }

            composable(Routes.REGISTER) {
                FormularioRegistroCliente(
                    onNavigateToSesion = {
                        navegarFooter(Routes.LOGIN)
                    },
                    onNavigateToMenu = {
                        navegarFooter(Routes.INICIO)
                    },
                    onNavigateToCategoria = {
                        navegarFooter(Routes.CATALOGO)
                    },
                    onNavigateToReserva = {
                        navegarFooter(Routes.RESERVA)
                    },
                    onNavigateToPerfil = {
                        navegarFooter(Routes.PERFIL)
                    }
                )
            }

            composable(Routes.PERFIL) {
                infoPerfil(
                    onNavigateToMenu = { navegarFooter(Routes.INICIO) },
                    onNavigateToCatalogo = { navegarFooter(Routes.CATALOGO) },
                    onNavigateToReserva = {
                        navegarFooter(Routes.RESERVAS_REALIZADAS)
                    },
                    onNavigateToLogin = {
                        reservaViewModel.setUsuario("")
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(com.example.app_en_desarollo.ui.navigation.Routes.INICIO) {
                InicioScreen(
                    onNavigateToLogin = { navController.navigate(Routes.RESERVA) },
                    onNavigateToRegister = {},
                    onNavigateToMenu = {
                        catalogoViewModel.cambiarSeccion("Menu")

                        navController.navigate(Routes.CATALOGO) {
                            popUpTo(Routes.INICIO) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToInicio = {},
                    onNavigateToCategoria = {
                        catalogoViewModel.cambiarSeccion("Reposteria")

                        navController.navigate(Routes.CATALOGO) {
                            popUpTo(Routes.INICIO) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToReserva = {
                        navegarFooter(Routes.RESERVAS_REALIZADAS)
                    },
                    onNavigateToPerfil = { navegarFooter(Routes.PERFIL) }
                )
            }
            composable(com.example.app_en_desarollo.ui.navigation.Routes.CATALOGO) {
                CatalogoScreen(
                    catalogoViewModel = catalogoViewModel,
                    onNavigateToLogin = { navController.navigate(Routes.RESERVA) },
                    onNavigateToRegister = {},
                    onNavigateToMenu = {},
                    onNavigateToInicio = {
                        navController.navigate(com.example.app_en_desarollo.ui.navigation.Routes.INICIO) {
                            popUpTo(com.example.app_en_desarollo.ui.navigation.Routes.INICIO) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToCategoria = {},
                    onNavigateToReserva = {
                        navegarFooter(Routes.RESERVAS_REALIZADAS)
                    },
                    onNavigateToPerfil = {
                        navegarFooter(Routes.PERFIL)
                    },
                    onNavigateToProducto = { nombre ->
                        navController.navigate(
                            "producto/" + Uri.encode(nombre)
                        )
                    }
                )
            }

            composable(com.example.app_en_desarollo.ui.navigation.Routes.PRODUCTO) { entrada ->
                val nombre = entrada.arguments?.getString("nombre") ?: ""
                val producto = catalogoViewModel.buscarProducto(nombre)

                if (producto != null) {
                    ProductosScreen(
                        producto = producto,
                        onNavigateToLogin = {},
                        onNavigateToRegister = {},
                        onNavigateToMenu = {},
                        onNavigateToInicio = {
                            navController.navigate(com.example.app_en_desarollo.ui.navigation.Routes.INICIO) {
                            }
                        },
                        onNavigateToCategoria = {},
                        onNavigateToReserva = {
                            navegarFooter(Routes.RESERVAS_REALIZADAS)
                        },
                        onNavigateToPerfil = { navegarFooter(Routes.PERFIL) },
                        onNavigateToProducto = { otroNombre ->
                            navController.navigate(
                                "producto/" + Uri.encode(otroNombre)
                            )
                        },
                        onNavigateToCatalogo = {
                            navController.popBackStack(
                                route = com.example.app_en_desarollo.ui.navigation.Routes.CATALOGO,
                                inclusive = false
                            )
                        }
                    )
                } else {
                    Text(text = "Producto no encontrado")
                }
            }
            composable(Routes.PERFIL) {
                infoPerfil(
                    onNavigateToMenu = {
                        navegarFooter(Routes.INICIO)
                    },
                    onNavigateToCatalogo = {
                        navegarFooter(Routes.CATALOGO)
                    },
                    onNavigateToReserva = {
                        navegarFooter(Routes.RESERVAS_REALIZADAS)
                    },
                    onNavigateToLogin = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Routes.RESERVA) {
                ReservaScreen(
                    onNavigateToObservaciones = { navController.navigate(Routes.OBSERVACIONES) },
                    onNavigateToCatalogo = { navegarFooter(Routes.CATALOGO) },
                    onReservaConfirmada = { navegarFooter(Routes.RESERVAS_REALIZADAS) },
                    onNavigateToReservas = { navegarFooter(Routes.RESERVAS_REALIZADAS) },
                    onNavigateToInicio = { navegarFooter(Routes.INICIO) },
                    onNavigateToPerfil = { navegarFooter(Routes.PERFIL) },
                    viewModel = reservaViewModel,
                    catalogoViewModel = catalogoViewModel
                )
            }

            composable(Routes.OBSERVACIONES) {
                ObservacionesScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToInicio = { navegarFooter(Routes.INICIO) },
                    onNavigateToCatalogo = { navegarFooter(Routes.CATALOGO) },
                    onNavigateToReservas = { navegarFooter(Routes.RESERVAS_REALIZADAS) },
                    onNavigateToPerfil = { navegarFooter(Routes.PERFIL) },
                    viewModel = reservaViewModel
                )
            }

            composable(Routes.TRANSFERENCIA) { entrada ->
                val reservaId = entrada.arguments?.getString("reservaId")?.toIntOrNull() ?: -1
                TransferenciaScreen(
                    reservaId = reservaId,
                    viewModel = reservaViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToReservas = {
                        navController.popBackStack()
                    },
                    onNavigateToInicio = { navegarFooter(Routes.INICIO) },
                    onNavigateToCatalogo = { navegarFooter(Routes.CATALOGO) },
                    onNavigateToPerfil = { navegarFooter(Routes.PERFIL) }
                )
            }

            composable(Routes.RESERVAS_REALIZADAS) {
                ReservasRealizadasScreen(
                    onNavigateToTransferencia = { id -> navController.navigate("transferencia/$id") },
                    onNavigateToNuevaReserva = {
                        navController.navigate(Routes.RESERVA) {
                            popUpTo(Routes.RESERVAS_REALIZADAS) { inclusive = true }
                        }
                    },
                    onNavigateToInicio = { navegarFooter(Routes.INICIO) },
                    onNavigateToCatalogo = { navegarFooter(Routes.CATALOGO) },
                    onNavigateToPerfil = { navegarFooter(Routes.PERFIL) },
                    viewModel = reservaViewModel
                )
            }
            composable(Routes.ADMIN){
                ScreenTrabajador(
                    onNavigateToCalendario = { navegarFooter(Routes.CALENDARIO_ADMIN) },
                    onNavigateToProductos = { navegarFooter(Routes.ADMIN_PRODUCTOS) },
                    onNavigateToCosto = { navController.navigate(Routes.COSTO) },
                    onNavigateToReservas = { navegarFooter(Routes.RESERVAS_ADMIN) },
                    onNavigateToDetalleTrabajador = { navController.navigate(Routes.DETALLE_TRABAJADOR) },
                    onNavigateToCrearTrabajador = { navController.navigate(Routes.CREAR_TRABAJADOR) },
                    onNavigateToModificarTrabajador = { navController.navigate(Routes.MODIFICAR_TRABAJADOR) },
                    viewModel = trabajadorViewModel
                )
            }

            composable(Routes.RESERVAS_ADMIN) {
                ReservasAdminScreen(
                    onVerDetalle = { reservaId ->
                        navController.navigate(
                            "detalle_reserva_admin/$reservaId"
                        )
                    },
                    onNavegar = { navegarAdmin(it) }
                )
            }
            composable(
                route = Routes.DETALLE_RESERVA_ADMIN,
                arguments = listOf(
                    navArgument("reservaId") {
                        type = NavType.IntType
                    }
                )
            ) { entrada ->
                val reservaId = entrada.arguments
                    ?.getInt("reservaId") ?: -1
                DetalleReservaAdminScreen(
                    reservaId = reservaId,
                    onVolver = {
                        navController.popBackStack()
                    },

                    onNavegar = { navegarAdmin(it) },
                    onVerComprobante = { comprobanteId ->
                        navController.navigate(
                            "comprobantes_admin/$comprobanteId"
                        )
                    }
                )
            }

            composable(
                route = Routes.COMPROBANTES_ADMIN,
                arguments = listOf(
                    navArgument("comprobanteId") {
                        type = NavType.LongType
                    }
                )
            ) { entrada ->
                val comprobanteId = entrada.arguments
                    ?.getLong("comprobanteId") ?: -1L

                ComprobantesAdminScreen(
                    comprobanteInicial = comprobanteId,
                    onVolver = {
                        navController.popBackStack()
                    },
                    onNavegar = { navegarAdmin(it) },
                )
            }

            composable(Routes.CALENDARIO_ADMIN) {
                CalendarioAdminScreen(
                    onVerDetalle = { reservaId ->
                        navController.navigate(
                            "detalle_reserva_admin/$reservaId"
                        )
                    },
                    onNavegar = { navegarAdmin(it) }
                )
            }

            composable(Routes.ADMIN_PRODUCTOS){
                AdminProductosScreen(
                    bottomBar = {
                        FooterAdmin(seleccionado = "Productos")
                    }
                )
            }

            composable(Routes.DETALLE_TRABAJADOR) {
                DetalleTrabajadorScreen(
                    onNavigateToProductos = { navegarFooter(Routes.ADMIN_PRODUCTOS) },
                    onNavigateToCosto = {
                        navController.navigate(Routes.COSTO)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    viewModel = trabajadorViewModel
                )
            }

            composable(Routes.CREAR_TRABAJADOR) {
                CrearTrabajadorScreen(
                    onNavigateToProductos = { navegarFooter(Routes.ADMIN_PRODUCTOS) },
                    onNavigateToCosto = {
                        navController.navigate(Routes.COSTO)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    viewModel = trabajadorViewModel
                )
            }

            composable(Routes.MODIFICAR_TRABAJADOR) {
                ModificarTrabajadorScreen(
                    onNavigateToProductos = { navegarFooter(Routes.ADMIN_PRODUCTOS) },
                    onNavigateToCosto = {
                        navController.navigate(Routes.COSTO)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    viewModel = trabajadorViewModel
                )
            }

            composable(Routes.COSTO) {
                ScreenCosto(
                    onNavigateToDetalleCosto = { navController.navigate(Routes.DETALLE_COSTO) },
                    onNavigateToPersonal = { navegarFooter(Routes.ADMIN) },
                    onNavigateToProductos = { navegarFooter(Routes.ADMIN_PRODUCTOS) },
                    viewModel = costoViewModel
                )
            }

            composable(Routes.DETALLE_COSTO) {
                DetalleCostoScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToResumenCosto = { navController.navigate(Routes.RESUMEN_COSTO) },
                    onNavigateToPersonal = { navegarFooter(Routes.ADMIN) },
                    onNavigateToProductos = { navegarFooter(Routes.ADMIN_PRODUCTOS) },
                    viewModel = costoViewModel
                )
            }

            composable(Routes.RESUMEN_COSTO) {
                ResumenCostoScreen(
                    onNavigateToProductos = { navegarFooter(Routes.ADMIN_PRODUCTOS) },
                    onNavigateToPersonal = {
                        navegarFooter(Routes.ADMIN)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    viewModel = costoViewModel
                )
            }
        }
    }
}