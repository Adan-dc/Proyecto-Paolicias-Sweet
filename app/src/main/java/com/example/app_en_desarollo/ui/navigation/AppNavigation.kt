package com.example.app_en_desarollo.ui.navigation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_en_desarollo.ui.screen.FormularioRegistroCliente
import com.example.app_en_desarollo.ui.screen.FormularioSesionCliente
import com.example.app_en_desarollo.ui.screen.MenuProductos
import com.example.app_en_desarollo.ui.screen.infoPerfil

object Routes{
    const val INICIO = "inicio"

    const val REGISTER = "register"

    const val MENU = "menu"

    const val PERFIL = "perfil"

    const val CATALOGO = "catalogo"

    const val RESERVA = "reserva"

    const val LOGIN = "login"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN, //Pantalla de inicio
        modifier = Modifier.fillMaxSize()
    ){
        composable(Routes.LOGIN){
            FormularioSesionCliente (
                onNavigateToMenu = {
                    navController.navigate(Routes.MENU)
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onNavigateToCatalogo = {
                    navController.navigate(Routes.CATALOGO)
                },
                onNavigateToReserva = {
                    navController.navigate(Routes.RESERVA)
                },
                onNavigateToPerfil = {
                    navController.navigate(Routes.PERFIL)
                }
            )
        }

        composable(Routes.REGISTER){
            FormularioRegistroCliente(
                onNavigateToSesion = {
                    navController.navigate(Routes.LOGIN)
                },
                onNavigateToMenu = {
                    navController.navigate(Routes.MENU)
                },
                onNavigateToCategoria = {
                    navController.navigate(Routes.CATALOGO)
                },
                onNavigateToReserva = {
                    navController.navigate(Routes.RESERVA)
                },
                onNavigateToPerfil = {
                    navController.navigate(Routes.PERFIL)
                }
            )
        }

        composable(Routes.PERFIL){
            infoPerfil(
                onNavigateToMenu = {
                    navController.navigate(Routes.MENU)
                },
                onNavigateToCatalogo = {
                    navController.navigate(Routes.CATALOGO)
                },
                onNavigateToReserva = {
                    navController.navigate(Routes.RESERVA)
                },
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        composable(Routes.CATALOGO){
            MenuProductos(
                onNavigateToInicio = {
                    navController.navigate(Routes.LOGIN)
                },
                onNavigateToReserva = {
                    navController.navigate(Routes.RESERVA)
                },
                onNavigateToPerfil = {
                    navController.navigate(Routes.PERFIL)
                }
            )
        }
    }
}


