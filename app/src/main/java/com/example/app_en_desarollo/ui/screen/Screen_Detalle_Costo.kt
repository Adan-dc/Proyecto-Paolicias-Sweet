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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
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
fun DetalleCostoScreen(
    onNavigateBack: () -> Unit,
    onNavigateToResumenCosto: () -> Unit,
    onNavigateToPersonal: () -> Unit,
    viewModel: CostoViewModel
) {

    val productos = viewModel.productosCosto

    val productosAbiertos = remember {
        mutableStateMapOf<Long, Boolean>()
    }

    Scaffold(
        topBar = {
            Header(title = "DETALLE COSTO")
        },

        bottomBar = {
            FooterAdmin(
                onCalendarioClick = {},
                onProductosClick = {},
                onReservasClick = {},
                onCostoClick = {
                    onNavigateBack()
                },
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
                text = "Información del costo",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(productos) { producto ->

                    val abierto =
                        productosAbiertos[producto.productoId]
                            ?: false

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        productosAbiertos[
                                            producto.productoId
                                        ] = !abierto
                                    }
                                    .padding(12.dp),

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Checkbox(
                                    checked =
                                        viewModel.productoComprado(
                                            producto.productoId
                                        ),

                                    onCheckedChange = { marcado ->

                                        viewModel
                                            .cambiarProductoComprado(
                                                producto.productoId,
                                                marcado
                                            )
                                    }
                                )

                                Text(
                                    text = producto.nombreProducto,
                                    modifier = Modifier.weight(1f),
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Cantidad: ${producto.cantidad}",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = if (abierto) "  ▲" else "  ▼"
                                )
                            }


                            if (abierto) {

                                producto.ingredientes.forEach { ingrediente ->

                                    HorizontalDivider()

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),

                                        horizontalArrangement =
                                            Arrangement.SpaceBetween
                                    ) {

                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {

                                            Text(
                                                text = ingrediente.nombre,
                                                fontSize = 15.sp
                                            )

                                            Text(
                                                text =
                                                    "Cantidad: ${ingrediente.cantidad} ${ingrediente.unidadMedida}",
                                                fontSize = 13.sp
                                            )
                                        }

                                        Column(
                                            horizontalAlignment =
                                                Alignment.End
                                        ) {

                                            Text(
                                                text =
                                                    "Precio: ${formatoPeso(ingrediente.precioUnidad)}",
                                                fontSize = 13.sp
                                            )

                                            Text(
                                                text =
                                                    "Costo: ${formatoPeso(ingrediente.costo)}",
                                                fontSize = 13.sp
                                            )
                                        }
                                    }
                                }

                                HorizontalDivider()

                                Text(
                                    text =
                                        "Costo total: ${formatoPeso(producto.costoTotal)}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = {

                    viewModel.calcularCostoTotal {

                        onNavigateToResumenCosto()
                    }
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
                    text = "Calcular Costo Total",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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

private fun formatoPeso(valor: Double): String {

    return NumberFormat
        .getCurrencyInstance(
            Locale("es", "CL")
        )
        .format(valor)
}