package com.example.app_en_desarollo.ui.navigation


import android.net.Uri
<<<<<<< HEAD
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
=======
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
<<<<<<< HEAD
import androidx.compose.ui.platform.LocalContext
=======
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
<<<<<<< HEAD
import com.example.app_en_desarollo.data.preferences.SesionBiometrica
=======
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
import com.example.app_en_desarollo.ui.screen.CatalogoScreen
import com.example.app_en_desarollo.ui.screen.FormularioRegistroCliente
import com.example.app_en_desarollo.ui.screen.FormularioSesionCliente
import com.example.app_en_desarollo.ui.screen.InicioScreen
<<<<<<< HEAD
import com.example.app_en_desarollo.ui.screen.ObservacionesScreen
import com.example.app_en_desarollo.ui.screen.ReservaScreen
import com.example.app_en_desarollo.ui.screen.ReservasRealizadasScreen
import com.example.app_en_desarollo.ui.screen.infoPerfil
import com.example.app_en_desarollo.ui.viewmodel.CatalogoViewModel
import com.example.app_en_desarollo.ui.viewmodel.ReservaViewModel
=======
import com.example.app_en_desarollo.ui.screen.infoPerfil
import com.example.app_en_desarollo.ui.viewmodel.CatalogoViewModel
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
import com.gabriel.paoliciasSweet.ui.screen.ProductosScreen


object Routes{

    const val CATEGORIA = "categoria"

    const val PRODUCTO = "producto/{nombre}"

    const val INICIO = "inicio"
    const val REGISTER = "register"
    const val PERFIL = "perfil"

    const val CATALOGO = "catalogo"

    const val RESERVA = "reserva"

<<<<<<< HEAD
    const val OBSERVACIONES = "observaciones"

    const val RESERVAS_REALIZADAS = "reservas realizadas"

=======
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
    const val LOGIN = "login"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
<<<<<<< HEAD
    val context = LocalContext.current

    val catalogoViewModel: CatalogoViewModel = viewModel()
    val reservaViewModel: ReservaViewModel = viewModel()
=======
    val catalogoViewModel: CatalogoViewModel = viewModel()
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e

    fun navegarFooter(ruta: String){
        navController.navigate(ruta){
            popUpTo(Routes.CATALOGO){
                saveState =  true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

<<<<<<< HEAD
    val irAInicio = {
        navController.navigate(Routes.INICIO) {
            popUpTo(Routes.INICIO) { inclusive = false }
            launchSingleTop = true
        }
    }
    val irACatalogo = {
        catalogoViewModel.cambiarSeccion("Reposteria")
        navController.navigate(Routes.CATALOGO) { launchSingleTop = true }
    }
    val irAReservas = {
        navController.navigate(Routes.RESERVAS_REALIZADAS) { launchSingleTop = true }
    }
    val irAPerfil = { navegarFooter(Routes.PERFIL) }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN, //Pantalla de inicio
        modifier = Modifier.fillMaxSize(),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it / 3 }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it / 3 }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
=======
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN, //Pantalla de inicio
        modifier = Modifier.fillMaxSize()
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
    ){
        composable(Routes.LOGIN){
            FormularioSesionCliente (
                onNavigateToMenu = {
<<<<<<< HEAD

                    reservaViewModel.setUsuario(SesionBiometrica.obtenerEmail(context)?: "")
=======
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
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
<<<<<<< HEAD

        composable(Routes.PERFIL){
            infoPerfil(
                onNavigateToMenu = { navegarFooter(Routes.INICIO) },
                onNavigateToCatalogo = { navegarFooter(Routes.CATALOGO) },
                onNavigateToReserva = irAReservas,
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


=======
        composable(com.example.app_en_desarollo.ui.navigation.Routes.INICIO) {
            InicioScreen(
                onNavigateToLogin = {},
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
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
<<<<<<< HEAD
                onNavigateToReserva = {irAReservas()},
=======
                onNavigateToReserva = {},
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
                onNavigateToPerfil = {navegarFooter(Routes.PERFIL)}
            )
        }
        composable(com.example.app_en_desarollo.ui.navigation.Routes.CATALOGO) {
            CatalogoScreen(
                catalogoViewModel = catalogoViewModel,
                onNavigateToLogin = {},
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
<<<<<<< HEAD
                onNavigateToReserva = {irAReservas()},
=======
                onNavigateToReserva = {},
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
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
<<<<<<< HEAD
                    onNavigateToReserva = {irAReservas()},
=======
                    onNavigateToReserva = {},
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
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
                    navegarFooter(Routes.RESERVA)
                },
                //revisar esto tambien mandar a menu
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
<<<<<<< HEAD
        composable(Routes.RESERVA){
            ReservaScreen(
                onNavigateToObservaciones = { navController.navigate(Routes.OBSERVACIONES) },
                onNavigateToCatalogo = irACatalogo,
                onReservaConfirmada = { navController.navigate(Routes.RESERVAS_REALIZADAS) },
                onNavigateToReservas = irAReservas,
                onNavigateToInicio = irAInicio,
                onNavigateToPerfil = irAPerfil,
                viewModel = reservaViewModel,
                catalogoViewModel = catalogoViewModel
            )
        }

        composable(Routes.OBSERVACIONES){
            ObservacionesScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToInicio = irAInicio,
                onNavigateToCatalogo = irACatalogo,
                onNavigateToReservas = irAReservas,
                onNavigateToPerfil = irAPerfil,
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
                onNavigateToInicio = irAInicio,
                onNavigateToCatalogo = irACatalogo,
                onNavigateToReservas = irAReservas,
                onNavigateToPerfil = irAPerfil,
                viewModel = reservaViewModel
            )
        }
=======
>>>>>>> 02574050d3f5f556c5f98921c6471d1808f6db5e
    }
}


