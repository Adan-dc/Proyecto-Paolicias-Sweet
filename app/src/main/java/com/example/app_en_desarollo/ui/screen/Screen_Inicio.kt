package com.example.app_en_desarollo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.CatalogoProductos
import androidx.compose.runtime.setValue

//Acá recien filtramos por tipo de producto

@Composable
fun MenuProductos() {

    var categoriaSeleccionada by remember {
        mutableStateOf("Tortas")
    }

    val productosFiltrados = CatalogoProductos.productos.filter {
        it.Tipo == categoriaSeleccionada
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Catálogo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    categoriaSeleccionada = "Tortas"
                }
            ) {
                Text("Tortas")
            }

            Button(
                onClick = {
                    categoriaSeleccionada = "Canapes"
                }
            ) {
                Text("Canapés")
            }

            Button(
                onClick = {
                    categoriaSeleccionada = "Postres"
                }
            ) {
                Text("Postres")
            }

            Button(
                onClick = {
                    categoriaSeleccionada = "Otros"
                }
            ) {
                Text("Otros")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        productosFiltrados.forEach { producto ->

            Text(
                text = producto.Nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = producto.Descripcion
            )

            Text(
                text = "Precio: $${producto.PrecioUnitario}"
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    MenuProductos()
}