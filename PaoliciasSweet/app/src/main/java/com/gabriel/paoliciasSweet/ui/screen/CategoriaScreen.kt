package com.gabriel.paoliciasSweet.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabriel.paoliciasSweet.ui.viewmodel.CatalogoViewModel
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
    onNavigateToLogin: () -> Unit
){
    val context = LocalContext.current
    val catalogoViewModel: CatalogoViewModel = viewModel()
    Scaffold(
        topBar = { Header(title = "PAOLICIAS SWEET-APP") },
        bottomBar = { Footer() }
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
                    text="Selecciona los productos para tu evento",
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
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = producto.imagen),
                                contentDescription = producto.nombre,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),
                                contentScale = ContentScale.Crop
                            )

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
                        containerColor = Color(0xFFA83959),
                        contentColor = Color.White
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
    }
}


@Preview(showBackground = true)
@Composable
fun CategoriaScreenPreview(){
    com.gabriel.paoliciasSweet.ui.screen.CategoriaScreen(onNavigateToLogin = {})
}