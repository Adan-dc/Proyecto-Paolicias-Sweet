package com.gabriel.paoliciasSweet.ui.screen


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabriel.myapplication.R
import com.gabriel.paoliciasSweet.model.ProductosModel
import com.gabriel.paoliciasSweet.ui.components.Footer
import com.gabriel.paoliciasSweet.ui.components.Header
import com.gabriel.paoliciasSweet.ui.viewmodel.CatalogoViewModel

@Composable
fun InicioScreen(
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
            onInicioClick = {onNavigateToInicio()},
            onReservaClick = {},
            onCatalogoClick = {onNavigateToCategoria()},
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
                    text="¡Bienvenido!",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text="¡Todo para tu próximo evento!",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.inicio),
                    contentDescription = "Imagen del inicio",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .size(240.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        Toast.makeText(context, "¡ Llevandote a tu reserva !", Toast.LENGTH_SHORT).show()
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
                        text = "Crear una reserva",
                        fontSize = 16.sp,
                        letterSpacing = 2.sp
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text="Explora nuestro catálogo",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(18.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Card(
                            onClick = { onNavigateToMenu() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box {
                                Image(
                                    painter = painterResource(id = R.drawable.imagenmenu),
                                    contentDescription = "Imagen del inicio",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp),
                                )
                            }
                            Text(
                                text = "Menú",
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(14.dp)
                            )

                        }
                    }
                    item {
                        Card(
                            onClick = { onNavigateToCategoria() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box {
                                Image(
                                    painter = painterResource(id = R.drawable.imagenreposteria),
                                    contentDescription = "Imagen del inicio",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp),
                                )
                            }
                            Text(
                                text = "Repostería",
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(14.dp)
                            )

                        }
                    }
                }
            }//Column
        }//Box1
    }
}
@Preview(showBackground = true)
@Composable
fun InicioScrenPreview(){
    com.gabriel.paoliciasSweet.ui.screen.InicioScreen(onNavigateToLogin = {},onNavigateToRegister = {}, onNavigateToMenu= {}, onNavigateToInicio = {},
        onNavigateToCategoria = {}, onNavigateToReserva = {} ,onNavigateToPerfil = {})
}