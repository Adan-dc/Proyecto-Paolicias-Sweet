package com.example.app_en_desarollo.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.ui.components.FooterAdmin
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.CostoViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ResumenCostoScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPersonal: () -> Unit,
    viewModel: CostoViewModel
) {

    val productos = viewModel.productosCosto
    val calculoFinal = viewModel.calculoFinal

    Scaffold(

        topBar = {
            Header(title = "DETALLE COSTO")
        },

        bottomBar = {
            FooterAdmin(
                onCalendarioClick = {},
                onProductosClick = {},
                onReservasClick = {},
                onCostoClick = {},
                onPersonalClick = {
                    onNavigateToPersonal()
                }
            )
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
                text = "Información del costo\nTotal del evento en alimentos",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(4.dp)
                ) {

                    items(productos) { producto ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Text(
                                text = producto.nombreProducto,
                                modifier = Modifier.weight(1f),
                                fontSize = 15.sp
                            )

                            Column(
                                horizontalAlignment =
                                    Alignment.End
                            ) {

                                Text(
                                    text =
                                        "Cantidad: ${producto.cantidad}",
                                    fontSize = 14.sp
                                )

                                Text(
                                    text =
                                        "Precio Total: ${formatoPesoResumen(producto.costoTotal)}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        HorizontalDivider()
                    }

                    item {

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Text(
                            text =
                                "Costo total final: ${
                                    calculoFinal?.costoTotal
                                        ?.let {
                                            formatoPesoResumen(it)
                                        }
                                        ?: "-"
                                }",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Button(
                onClick = {
                    onNavigateBack()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),

                shape = CircleShape
            ) {

                Text(
                    text = "Volver",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


private fun formatoPesoResumen(valor: Double): String {

    return NumberFormat
        .getCurrencyInstance(
            Locale("es", "CL")
        )
        .format(valor)
}