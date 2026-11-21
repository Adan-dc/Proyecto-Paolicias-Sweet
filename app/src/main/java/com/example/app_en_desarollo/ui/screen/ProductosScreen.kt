package com.gabriel.paoliciasSweet.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.R
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.components.ImagenProducto
import com.example.app_en_desarollo.ui.viewmodel.CatalogoViewModel
import com.gabriel.paoliciasSweet.model.ProductosModel
import java.util.Locale

private fun formatoPrecio(precio: Int): String {
    return "$" + String.format(Locale.US, "%,d", precio).replace(",", ".")
}

@Composable
fun ProductosScreen(
    producto: ProductosModel,
    onNavigateToProducto: (String) -> Unit = {},
    onNavigateToCatalogo: () -> Unit = {},
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateToInicio: () -> Unit,
    onNavigateToCategoria: () -> Unit,
    onNavigateToReserva: () -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val context = LocalContext.current
    val catalogoViewModel: CatalogoViewModel = viewModel()

    val otros = catalogoViewModel.obtenerProductosFiltrados()
        .filter { it.nombre != producto.nombre }

    Scaffold(
        topBar = { Header(title = "PAOLICIAS SWEET-APP") },
        bottomBar = {
            Footer(
                onInicioClick = { onNavigateToInicio() },
                onReservaClick = { onNavigateToReserva() },
                onCatalogoClick = { onNavigateToCatalogo() },
                onPerfilClick = { onNavigateToPerfil() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC5D3)),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Producto",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFE8EE)
                            )
                        ) {
                            ImagenProducto(
                                imagenUrl = producto.imagenUrl,
                                descripcion = producto.nombre,
                                fallback = producto.imagen,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                            )

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = producto.nombre,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = formatoPrecio(producto.precio),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFA83959)
                                )

                                if (producto.descripcion.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = producto.descripcion,
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = if (producto.seccion == "Packs") "Incluye:" else "Ingredientes orientativos:",
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(text = producto.ingredientes)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Otros productos",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                items(otros) { otro ->
                    Card(
                        onClick = {
                            onNavigateToProducto(otro.nombre)
                        },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ImagenProducto(
                            imagenUrl = otro.imagenUrl,
                            descripcion = otro.nombre,
                            fallback = otro.imagen,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                        )

                        Text(
                            text = otro.nombre,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            minLines = 2,
                            maxLines = 2,
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 10.dp)
                        )

                        Text(
                            text = formatoPrecio(otro.precio),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFA83959),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                Toast.makeText(context, "¡ Yendo al catalogo !", Toast.LENGTH_SHORT).show()
                                onNavigateToCatalogo()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .shadow(8.dp, shape = CircleShape),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "Descubrir más productos",
                                fontSize = 16.sp,
                                letterSpacing = 2.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductosScrenPreview() {
    ProductosScreen(
        producto = ProductosModel(
            nombre = "Tarta de fresas y vainilla",
            tipo = "tarta",
            precio = 5000,
            ingredientes = "Fresas\nVainilla\nHarina\nAzúcar",
            imagen = R.drawable.tartadefresasyvainilla
        ),
        onNavigateToLogin = {},
        onNavigateToRegister = {},
        onNavigateToMenu = {},
        onNavigateToInicio = {},
        onNavigateToCategoria = {},
        onNavigateToReserva = {},
        onNavigateToPerfil = {}
    )
}