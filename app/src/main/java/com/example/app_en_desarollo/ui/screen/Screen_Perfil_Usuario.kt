package com.example.app_en_desarollo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.LoginViewModel

@Composable
fun infoPerfil(
    onNavigateToMenu: () -> Unit,
    onNavigateToCatalogo: () -> Unit,
    onNavigateToReserva: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val state = viewModel.uiState
    Scaffold(
        topBar = { Header(title = "INICIO SESIÓN") },
        bottomBar = {
            Footer(
                onInicioClick = {
                    onNavigateToMenu()
                },

                onCatalogoClick = {
                    onNavigateToCatalogo()
                },

                onReservaClick = {
                    onNavigateToReserva()
                },

                onPerfilClick = {
                }
            )
        }
    ) {
            innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC5D3)),
            contentAlignment = Alignment.Center
        ) {

        }
    }
}