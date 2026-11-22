package com.example.app_en_desarollo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.ReservaViewModel

@Composable
fun ObservacionesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToInicio: () -> Unit = {},
    onNavigateToCatalogo: () -> Unit = {},
    onNavigateToReservas: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    viewModel: ReservaViewModel = viewModel()
){
    val state = viewModel.uiState

    Scaffold(
        containerColor = Color(0xFFFBE9EC),
        topBar = { Header(title = "Paolicias-Sweet APP", backgroundColor = Color(0xFFFBE9EC), textColor = Color(0xFF5A1F33)) },
        bottomBar = {
            Footer(
                onInicioClick = { onNavigateToInicio() },
                onReservaClick = { onNavigateToReservas() },
                onCatalogoClick = { onNavigateToCatalogo() },
                onPerfilClick = { onNavigateToPerfil() }
            )        }
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Observaciones especiales",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF5A1F33)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Opciones",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF5A1F33)
            )
            Text(
                text = "Ingrese los requerimientos especiales que deberán tener en cuenta para el evento.",
                fontSize = 10.sp,
                color = Color(0xFF8A6A72)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFF8F6))
                    .padding(vertical = 6.dp)
            ) {
                state.observaciones.forEachIndexed { index, texto ->
                    TextField(
                        value = texto,
                        onValueChange = { viewModel.onObservacionChanged(index, it) },
                        placeholder = { Text(text = placeholderObservacion(index), fontSize = 13.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = coloresCampo()
                    )
                    HorizontalDivider(color = Color(0xFFF4CFD7))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onAgregarObservacionClick() }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = Color(0xFF5A1F33),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Agregar otro requerimiento", fontSize = 13.sp, color = Color(0xFF8A6A72))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onNavigateBack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA63A5B),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Confirmar observaciones especiales", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun coloresCampo() = TextFieldDefaults.colors(
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    focusedTextColor = Color(0xFF5A1F33),
    unfocusedTextColor = Color(0xFF5A1F33),
    focusedPlaceholderColor = Color(0xFF8A6A72),
    unfocusedPlaceholderColor = Color(0xFF8A6A72),
    cursorColor = Color(0xFFA63A5B)
)

private fun placeholderObservacion(index: Int): String = when (index) {
    0 -> "Ej: Restricción alimentaria"
    1 -> "Ej: Alergia"
    else -> "Ej: Otro requerimiento"
}