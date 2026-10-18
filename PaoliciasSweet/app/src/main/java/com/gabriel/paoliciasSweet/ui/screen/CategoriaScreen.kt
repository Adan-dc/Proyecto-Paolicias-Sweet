package com.gabriel.paoliciasSweet.ui.screen
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
import com.gabriel.paoliciasSweet.ui.viewmodel.CatalogoViewModel
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
import com.gabriel.paoliciasSweet.ui.components.Footer
import com.gabriel.paoliciasSweet.ui.components.Header
@Composable
fun CategoriaScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateToInicio: () -> Unit,
    onNavigateToCategoria: () -> Unit,
    onNavigateToReserva: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    onNavigateToProducto: (String) -> Unit = {}
){
    val context = LocalContext.current
    val catalogoViewModel: CatalogoViewModel = viewModel()
    var productoElegido by remember {
        mutableStateOf<ProductosModel?>(null)
    }
    var cantidad by remember { mutableStateOf(1) }
    Scaffold(
        topBar = { Header(title = "PAOLICIAS SWEET-APP") },
        bottomBar = {  Footer(
            onInicioClick = {},
            onReservaClick = {},
            onCatalogoClick = {},
            onPerfilClick = {}
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = catalogoViewModel.categoriaSeleccionada == "Todas",
                        onClick = { catalogoViewModel.cambiarCategoria("Todas") },
                        label = { Text("Todas") }
                    )

                    FilterChip(
                        selected = catalogoViewModel.categoriaSeleccionada == "Tarta",
                        onClick = { catalogoViewModel.cambiarCategoria("Tarta") },
                        label = { Text("Tartas") }
                    )

                    FilterChip(
                        selected = catalogoViewModel.categoriaSeleccionada == "Galleta",
                        onClick = { catalogoViewModel.cambiarCategoria("Galleta") },
                        label = { Text("Galletas") }
                    )
                    FilterChip(
                        selected = catalogoViewModel.categoriaSeleccionada == "Bizcocho",
                        onClick = { catalogoViewModel.cambiarCategoria("Bizcocho") },
                        label = { Text("Bizcochos") }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text="Selecciona los productos para tu evento\n" +
                            "(Los productos a los que se le presione el corazon se" +
                            "agregaran al evento)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
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
                                Image(
                                    painter = painterResource(id = producto.imagen),
                                    contentDescription = producto.nombre,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp),
                                    contentScale = ContentScale.Crop
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

                            Text(
                                text = "$" + producto.precio,
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    end = 12.dp,
                                    bottom = 12.dp
                                )
                            )
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
            AlertDialog(
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


@Preview(showBackground = true)
@Composable
fun CategoriaScreenPreview(){
    com.gabriel.paoliciasSweet.ui.screen.CategoriaScreen(onNavigateToLogin = {},onNavigateToRegister = {}, onNavigateToMenu= {}, onNavigateToInicio = {},
        onNavigateToCategoria = {}, onNavigateToReserva = {} ,onNavigateToPerfil = {})
}