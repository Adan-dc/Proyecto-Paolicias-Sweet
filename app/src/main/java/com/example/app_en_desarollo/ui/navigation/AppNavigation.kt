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
import com.example.app_en_desarollo.data.preferences.SesionBiometrica
import com.example.app_en_desarollo.ui.screen.CatalogoScreen
import com.example.app_en_desarollo.ui.screen.FormularioRegistroCliente
import com.example.app_en_desarollo.ui.screen.FormularioSesionCliente
import com.example.app_en_desarollo.ui.screen.InicioScreen
import com.example.app_en_desarollo.ui.screen.ObservacionesScreen
import com.example.app_en_desarollo.ui.screen.ReservaScreen
import com.example.app_en_desarollo.ui.screen.ReservasRealizadasScreen
import com.example.app_en_desarollo.ui.screen.infoPerfil
import com.example.app_en_desarollo.ui.viewmodel.CatalogoViewModel
import com.example.app_en_desarollo.ui.viewmodel.ReservaViewModel
import com.gabriel.paoliciasSweet.ui.screen.ProductosScreen


object Routes{

    const val CATEGORIA = "categoria"

    const val PRODUCTO = "producto/{nombre}"

    const val INICIO = "inicio"
    const val REGISTER = "register"
    const val PERFIL = "perfil"

    const val CATALOGO = "catalogo"

    const val RESERVA = "reserva"

    const val OBSERVACIONES = "observaciones"

    const val RESERVAS_REALIZADAS = "reservas realizadas"

    const val LOGIN = "login"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val context = LocalContext.current

    val catalogoViewModel: CatalogoViewModel = viewModel()
    val reservaViewModel: ReservaViewModel = viewModel()

    fun navegarFooter(ruta: String){
        navController.navigate(ruta){
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it / 3 }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it / 3 }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
    ){
        composable(Routes.LOGIN){
            FormularioSesionCliente (
                onNavigateToMenu = {

                    reservaViewModel.setUsuario(SesionBiometrica.obtenerEmail(context)?: "")
                    navController.navigate(Routes.INICIO) {
                        popUpTo(Routes.LOGIN){
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
                }
            )
        }

        composable(Routes.REGISTER){
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

        composable(Routes.PERFIL){
            infoPerfil(
                onNavigateToMenu = { navegarFooter(Routes.INICIO) },
                onNavigateToCatalogo = { navegarFooter(Routes.CATALOGO) },
                onNavigateToReserva = {
                    navegarFooter(Routes.RESERVAS_REALIZADAS)
                },
                onNavigateToLogin = {
                    reservaViewModel.setUsuario("")
                    navController.navigate(Routes.LOGIN){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(com.example.app_en_desarollo.ui.navigation.Routes.INICIO) {
            InicioScreen(
                onNavigateToLogin = {navController.navigate(Routes.RESERVA)},
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
                onNavigateToPerfil = {navegarFooter(Routes.PERFIL)}
            )
        }
        composable(com.example.app_en_desarollo.ui.navigation.Routes.CATALOGO) {
            CatalogoScreen(
                catalogoViewModel = catalogoViewModel,
                onNavigateToLogin = {navController.navigate(Routes.RESERVA)},
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
                    onNavigateToPerfil = {navegarFooter(Routes.PERFIL)},
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
        composable(Routes.PERFIL){
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
                    navController.navigate(Routes.LOGIN){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.RESERVA){
            ReservaScreen(
                onNavigateToObservaciones = { navController.navigate(Routes.OBSERVACIONES) },
                onNavigateToCatalogo = {navegarFooter(Routes.CATALOGO)},
                onReservaConfirmada = { navegarFooter(Routes.RESERVAS_REALIZADAS) },
                onNavigateToReservas = {navegarFooter(Routes.RESERVAS_REALIZADAS)},
                onNavigateToInicio = {navegarFooter(Routes.INICIO)},
                onNavigateToPerfil = {navegarFooter(Routes.PERFIL)},
                viewModel = reservaViewModel,
                catalogoViewModel = catalogoViewModel
            )
        }

        composable(Routes.OBSERVACIONES){
            ObservacionesScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToInicio = {navegarFooter(Routes.INICIO)},
                onNavigateToCatalogo = {navegarFooter(Routes.CATALOGO)},
                onNavigateToReservas = {navegarFooter(Routes.RESERVAS_REALIZADAS)},
                onNavigateToPerfil = {navegarFooter(Routes.PERFIL)},
                viewModel = reservaViewModel
            )
        }

        composable(Routes.RESERVAS_REALIZADAS){
            ReservasRealizadasScreen(
                onNavigateToNuevaReserva = {
                    navController.navigate(Routes.RESERVA) {
                        popUpTo(Routes.RESERVAS_REALIZADAS) { inclusive = true }
                    }
                },
                onNavigateToInicio = {navegarFooter(Routes.INICIO)},
                onNavigateToCatalogo = {navegarFooter(Routes.CATALOGO)},
                onNavigateToPerfil = {navegarFooter(Routes.PERFIL)},
                viewModel = reservaViewModel
            )
        }
    }
}


