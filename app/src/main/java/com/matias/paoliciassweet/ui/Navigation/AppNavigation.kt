package com.matias.paoliciassweet.Navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.matias.paoliciassweet.Screen.CategoriaScreen
import com.matias.paoliciassweet.Screen.ObservacionesScreen
import com.matias.paoliciassweet.Screen.ProductosScreen
import com.matias.paoliciassweet.Screen.ReservaScreen
import com.matias.paoliciassweet.ui.Screen.ReservasRealizadasScreen
import com.matias.paoliciassweet.ui.viewmodel.CatalogoViewModel
import com.matias.paoliciassweet.ui.viewmodel.ReservaViewModel

object Routes{
    const val RESERVA = "reserva"
    const val OBSERVACIONES = "observaciones"
    const val RESERVAS_REALIZADAS = "reservas_realizadas"
    const val CATEGORIA = "categoria"
    const val PRODUCTO = "producto/{nombre}"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    val reservaViewModel: ReservaViewModel = viewModel()
    val catalogoViewModel: CatalogoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.RESERVA,
        modifier = Modifier.fillMaxSize()
    ){
        composable(Routes.RESERVA){
            ReservaScreen(
                onNavigateToObservaciones = { navController.navigate(Routes.OBSERVACIONES) },
                onNavigateToCatalogo = { navController.navigate(Routes.CATEGORIA) },
                onReservaConfirmada = { navController.navigate(Routes.RESERVAS_REALIZADAS) },
                viewModel = reservaViewModel,
                catalogoViewModel = catalogoViewModel
            )
        }

        composable(Routes.OBSERVACIONES){
            ObservacionesScreen(
                onNavigateBack = { navController.popBackStack() },
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
                viewModel = reservaViewModel
            )
        }

        composable(Routes.CATEGORIA){
            CategoriaScreen(
                onNavigateToProducto = { nombre ->
                    navController.navigate("producto/" + Uri.encode(nombre))
                },
                onNavigateBack = { navController.popBackStack() },
                catalogoViewModel = catalogoViewModel
            )
        }

        composable(Routes.PRODUCTO){ entrada ->
            val nombre = entrada.arguments?.getString("nombre") ?: ""
            val producto = catalogoViewModel.buscarProducto(nombre)

            if (producto != null) {
                ProductosScreen(
                    producto = producto,
                    onNavigateToProducto = { otro ->
                        navController.navigate("producto/" + Uri.encode(otro))
                    },
                    onNavigateToCatalogo = {
                        navController.popBackStack(route = Routes.CATEGORIA, inclusive = false)
                    },
                    catalogoViewModel = catalogoViewModel
                )
            } else {
                Text(text = "Producto no encontrado")
            }
        }
    }
}
