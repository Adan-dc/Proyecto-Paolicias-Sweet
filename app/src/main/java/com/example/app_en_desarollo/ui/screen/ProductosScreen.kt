package com.gabriel.paoliciasSweet.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.app_en_desarollo.R
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.CatalogoViewModel
import com.gabriel.paoliciasSweet.model.ProductosModel
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
){
    val context = LocalContext.current
    val catalogoViewModel: CatalogoViewModel = viewModel()
    Scaffold(
        topBar = { Header(title = "PAOLICIAS SWEET-APP") },
        bottomBar = {  Footer(
            onInicioClick = { onNavigateToInicio() },
            onReservaClick = { onNavigateToReserva() },
            onCatalogoClick = { onNavigateToCatalogo() },
            onPerfilClick = { onNavigateToPerfil() }
        )}
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
                    text="Producto",
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
                    Image(
                        painter = painterResource(id = producto.imagen),
                        contentDescription = producto.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
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
                            text = "$" + producto.precio,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Ingredientes orientativos:",
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(text = producto.ingredientes)
                    }
                }
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
                            onClick = {
                                onNavigateToProducto(producto.nombre)
                            },
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
                        containerColor = Color.White, //0xFFA83959
                        contentColor = Color.Black
                    ),
                    shape = CircleShape
                ){
                    Text(
                        text = "Descubrir más productos",
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
fun ProductosScrenPreview() {
    ProductosScreen(
        producto = ProductosModel(
            nombre = "Tarta de fresas y vainilla",
            tipo = "tarta",
            precio = 5000,
            ingredientes = "Fresas\nVainilla\nHarina\nAzúcar",
            imagen = R.drawable.tartadefresasyvainilla
        ),
        onNavigateToLogin = {},onNavigateToRegister = {}, onNavigateToMenu= {}, onNavigateToInicio = {},
        onNavigateToCategoria = {}, onNavigateToReserva = {} ,onNavigateToPerfil = {}
    )
}