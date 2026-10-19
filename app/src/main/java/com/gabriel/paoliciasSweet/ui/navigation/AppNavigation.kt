package com.gabriel.paoliciasSweet.ui.navigation

import android.net.Uri
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gabriel.paoliciasSweet.ui.screen.CategoriaScreen
import com.gabriel.paoliciasSweet.ui.screen.ProductosScreen
import com.gabriel.paoliciasSweet.ui.viewmodel.CatalogoViewModel

object Routes {
    const val CATEGORIA = "categoria"
    const val PRODUCTO = "producto/{nombre}"

    const val INICIO = "inicio"

    const val REGISTER = "register"

    const val MENU = "menu"

    const val PERFIL = "perfil"

    const val CATALOGO = "catalogo"

    const val RESERVA = "reserva"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val catalogoViewModel: CatalogoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.CATEGORIA // Temporal para probar
    ) {
        composable(Routes.CATEGORIA) {
            CategoriaScreen(
                onNavigateToLogin = {},
                onNavigateToRegister = {},
                onNavigateToMenu = {},
                onNavigateToInicio = {},
                onNavigateToCategoria = {},
                onNavigateToReserva = {},
                onNavigateToPerfil = {},
                onNavigateToProducto = { nombre ->
                    navController.navigate(
                        "producto/" + Uri.encode(nombre)
                    )
                }
            )
        }

        composable(Routes.PRODUCTO) { entrada ->
            val nombre = entrada.arguments?.getString("nombre") ?: ""
            val producto = catalogoViewModel.buscarProducto(nombre)

            if (producto != null) {
                ProductosScreen(
                    producto = producto,
                    onNavigateToLogin = {},
                    onNavigateToRegister = {},
                    onNavigateToMenu = {},
                    onNavigateToInicio = {},
                    onNavigateToCategoria = {},
                    onNavigateToReserva = {},
                    onNavigateToPerfil = {},
                    onNavigateToProducto = { otroNombre ->
                        navController.navigate(
                            "producto/" + Uri.encode(otroNombre)
                        )
                    },
                    onNavigateToCatalogo = {
                        navController.popBackStack(
                            route = Routes.CATEGORIA,
                            inclusive = false
                        )
                    }
                )
            } else {
                Text(text = "Producto no encontrado")
            }
        }
    }
}