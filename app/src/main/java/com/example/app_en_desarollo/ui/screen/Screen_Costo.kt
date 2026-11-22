package com.example.app_en_desarollo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.ui.components.FooterAdmin
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.CostoViewModel

@Composable
fun ScreenCosto(
    onNavigateToDetalleCosto: () -> Unit,
    onNavigateToPersonal: () -> Unit,
    onNavigateToProductos: () -> Unit,
    viewModel: CostoViewModel
) {

    val reservas = viewModel.reservas

    LaunchedEffect(Unit) {
        viewModel.listarReservasVigentes()
    }

    Scaffold(

        topBar = {
            Header(title = "COSTOS")
        },

        bottomBar = {

            FooterAdmin(seleccionado = "Costo")
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC5D3))
                .padding(20.dp)
        ) {

            Text(
                text = "Lista de reservas",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(reservas) { reserva ->

                    val idReserva = reserva.id

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                viewModel.seleccionarReserva(reserva)

                                viewModel.cargarDetalleCosto()

                                onNavigateToDetalleCosto()
                            }
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Checkbox(
                                checked =
                                    if (idReserva != null) {
                                        viewModel.reservaComprada(idReserva)
                                    } else {
                                        false
                                    },

                                onCheckedChange = { marcado ->

                                    idReserva?.let { id ->

                                        viewModel.cambiarReservaComprada(
                                            id,
                                            marcado
                                        )
                                    }
                                }
                            )

                            Column(
                                modifier = Modifier.padding(start = 12.dp)
                            ) {

                                Text(
                                    text = reserva.tipoEvento,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = reserva.fecha,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}