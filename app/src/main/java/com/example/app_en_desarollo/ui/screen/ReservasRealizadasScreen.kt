package com.example.app_en_desarollo.ui.screen

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.R
import com.example.app_en_desarollo.model.ReservaGuardada
import com.example.app_en_desarollo.model.ReservaModel
import com.example.app_en_desarollo.ui.viewmodel.ReservaViewModel


@Composable
fun ReservasRealizadasScreen(
    onNavigateToNuevaReserva: () -> Unit,
    onNavigateToInicio: () -> Unit = {},
    onNavigateToCatalogo: () -> Unit = {},
    onNavigateToReservas: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    viewModel: ReservaViewModel = viewModel()
){
    val state = viewModel.uiState
    val context = LocalContext.current
    LaunchedEffect(Unit) { viewModel.cargarReservas(context) }

    Scaffold(
        containerColor = Color(0xFFFBE9EC),
        topBar = { Header(title = "Paolicias-Sweet APP", backgroundColor = Color(0xFFFBE9EC), textColor = Color(0xFF5A1F33)) },
        bottomBar = {
            Footer(
                onInicioClick = { onNavigateToInicio() },
                onReservaClick = {},
                onCatalogoClick = { onNavigateToCatalogo() },
                onPerfilClick = { onNavigateToPerfil() }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item{
                Text(
                    text = "Reservas realizadas",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF5A1F33)
                )
                Text(
                    text ="Revisar tus reservas ya abonadas/pagadas.",
                    fontSize =10.sp,
                    color=Color(0xFF8A6A72)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(state.reservasGuardadas){ reserva ->
                TarjetaReserva(reserva)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Button(
                    onClick = {onNavigateToNuevaReserva()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFA63A5B),
                        contentColor = Color.White
                    )
                ){
                    Text(text = "Realizar una nueva reserva", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


@Composable
private fun TarjetaReserva(reserva: ReservaGuardada){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFFFF8F6))
    ) {
        Image(
            painter = painterResource(id = R.drawable.evento_boda),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = reserva.tipoEvento,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color=Color(0xFF5A1F33)
            )
            Spacer(modifier = Modifier.height(4.dp))
            val gris = Color(0xFF8A6A72)
            Text(text = "ID evento: ${reserva.id}", fontSize = 12.sp, color=gris)
            Text(text = "Fecha: ${reserva.fecha}", fontSize = 12.sp, color=gris)
            Text(text = "Horario: ${reserva.hora}", fontSize = 12.sp, color=gris)
            Text(text = "Cantidad de invitados: ${reserva.asistentes}", fontSize = 12.sp, color=gris)
            Text(text = "Observaciones especiales: ${if (reserva.tieneObservaciones) "SI" else "NO"} ", fontSize = 12.sp, color=gris)
        }
    }
}

