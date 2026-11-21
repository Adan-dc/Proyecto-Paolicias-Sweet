package com.example.app_en_desarollo.ui.screen



import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.style.TextOverflow
import java.util.Locale
import com.example.app_en_desarollo.ui.components.ImagenProducto
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.gabriel.paoliciasSweet.model.ProductosModel
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.CatalogoViewModel
@Composable
fun CatalogoScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateToInicio: () -> Unit,
    onNavigateToCategoria: () -> Unit,
    onNavigateToReserva: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    onNavigateToProducto: (String) -> Unit = {},
    catalogoViewModel: CatalogoViewModel = viewModel()
){
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        catalogoViewModel.cargarProductos()
    }
    var productoElegido by remember {
        mutableStateOf<ProductosModel?>(null)
    }
    var cantidad by remember { mutableStateOf(1) }
    Scaffold(
        topBar = { Header(title = "PAOLICIAS SWEET-APP") },
        bottomBar = {  Footer(
            onInicioClick = {onNavigateToInicio()},
            onReservaClick = { onNavigateToReserva() },
            onCatalogoClick = {},
            onPerfilClick = { onNavigateToPerfil()}
        ) }
    ) {
            innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC5D3)),
            contentAlignment = Alignment.Center
        ){
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){
                Text(
                    text="Catalogo",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                val secciones = listOf(
                    "Packs" to "Packs",
                    "Menu" to "Menú",
                    "Reposteria" to "Repostería",
                    "Bebestibles" to "Bebestibles"
                )

                val categorias = when (catalogoViewModel.seccionSeleccionada) {
                    "Packs" -> listOf(
                        "ESTANDAR" to "Estándar",
                        "PREMIUM" to "Premium",
                        "VEGETARIANO" to "Vegetariano",
                        "PROMO" to "Promos"
                    )
                    "Reposteria" -> listOf(
                        "Tarta" to "Tartas",
                        "Galleta" to "Galletas",
                        "Bizcocho" to "Bizcochos"
                    )
                    "Bebestibles" -> listOf(
                        "Jugo" to "Jugos",
                        "Cafe" to "Café",
                        "Limonada" to "Limonadas"
                    )
                    else -> listOf(
                        "Entrada" to "Entradas",
                        "Plato de fondo" to "Plato de fondo",
                        "Ensalada" to "Ensaladas"
                    )
                }

                FilaFiltros(
                    opciones = secciones,
                    seleccionado = catalogoViewModel.seccionSeleccionada,
                    onSeleccion = {
                        catalogoViewModel.cambiarSeccion(it)
                        catalogoViewModel.cambiarCategoria("Todas")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                FilaFiltros(
                    opciones = listOf("Todas" to "Todas") + categorias,
                    seleccionado = catalogoViewModel.categoriaSeleccionada,
                    onSeleccion = { catalogoViewModel.cambiarCategoria(it) }
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Toca el corazón de los productos que quieras agregar a tu evento",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF5A1F33)
                )
                Spacer(modifier = Modifier.height(24.dp))
                if (catalogoViewModel.cargando) {
                    Text("Cargando...")
                }
                catalogoViewModel.error?.let {
                    Text(text = it, fontSize = 13.sp, color = Color(0xFFD32F2F))
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(catalogoViewModel.obtenerProductosFiltrados()) { producto ->
                        val estaAgregado = catalogoViewModel.productosReserva.any { reservado ->
                            reservado.producto.nombre == producto.nombre
                        }
                        Card(
                            onClick = {
                                onNavigateToProducto(producto.nombre)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box {
                                ImagenProducto(
                                    imagenUrl = producto.imagenUrl,
                                    descripcion = producto.nombre,
                                    fallback = producto.imagen,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                )

                                IconButton(
                                    onClick = {
                                        cantidad = 1

                                        for (agregado in catalogoViewModel.productosReserva) {
                                            if (agregado.producto.nombre == producto.nombre) {
                                                cantidad = agregado.cantidad
                                            }
                                        }
                                        productoElegido = producto
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                        .size(36.dp)
                                        .background(
                                            color = if (estaAgregado) Color(0xFFA83959) else Color.White,
                                            shape = CircleShape
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Añadir a la reserva",
                                        tint = if (estaAgregado) Color.White else Color(0xFFA83959)
                                    )
                                }
                            }
                            Text(
                                text = producto.nombre,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(12.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "$" + producto.precio)
                                Text(text = producto.descripcion, fontSize = 12.sp)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        Toast.makeText(context, "¡ Bienvenido !", Toast.LENGTH_SHORT).show()
                        onNavigateToLogin()
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
                ){
                    Text(
                        text = "Confirmar reserva",
                        fontSize = 16.sp,
                        letterSpacing = 2.sp
                    )
                }//Button
            }//Column
        }//Box1
        val productoActual = productoElegido

        if (productoActual != null) {
            val animacion = remember { Animatable(0.8f) }

            LaunchedEffect(Unit) {
                animacion.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(800)
                )
            }
            AlertDialog(
                modifier = Modifier.graphicsLayer {
                    scaleX = animacion.value
                    scaleY = animacion.value
                },
                onDismissRequest = {
                    productoElegido = null
                },
                title = {
                    Text(text = productoActual.nombre)
                },
                text = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { cantidad-- },
                            enabled = cantidad > 1
                        ) {
                            Text("-")
                        }

                        Text(
                            text = cantidad.toString(),
                            fontSize = 22.sp
                        )

                        TextButton(onClick = { cantidad++ }) {
                            Text("+")
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            catalogoViewModel.agregarProducto(
                                productoActual,
                                cantidad
                            )
                            productoElegido = null
                        }
                    ) {
                        Text("Añadir")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { productoElegido = null }
                    ) {
                        Text("Cancelar")
                    }
                }
            )

        }

    }

}
@Composable
fun FilaFiltros(
    opciones: List<Pair<String, String>>,
    seleccionado: String,
    onSeleccion: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        opciones.forEach { (valor, etiqueta) ->
            FilterChip(
                selected = seleccionado == valor,
                onClick = { onSeleccion(valor) },
                label = { Text(etiqueta) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoScreenPreview(){
    CatalogoScreen(
        onNavigateToLogin = {},
        onNavigateToRegister = {},
        onNavigateToMenu = {},
        onNavigateToInicio = {},
        onNavigateToCategoria = {},
        onNavigateToReserva = {},
        onNavigateToPerfil = {})
}