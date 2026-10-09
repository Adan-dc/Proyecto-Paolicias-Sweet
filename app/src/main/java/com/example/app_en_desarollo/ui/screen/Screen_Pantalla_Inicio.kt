package com.example.app_en_desarollo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.app_en_desarollo.R
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header


@Composable
fun PantallaInicialScreen(
    onNavigateToLogin: () -> Unit
){
    val context = LocalContext.current
    Scaffold(
        topBar = { Header(title = "PAOLICIAS-SWEET APP") },
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.emblema_paolicias_sweet),
                    contentDescription = "Logo de paolicias_sweet",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(240.dp)
                        .shadow(elevation = 12.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .border(4.dp, Color.Black, CircleShape)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        Toast.makeText(context, "¡ Bienvenido !", Toast.LENGTH_SHORT).show()
                        onNavigateToLogin()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(56.dp)
                        .shadow(8.dp, shape = CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(2.dp, Color.White),
                    shape = CircleShape
                ){
                    Text(
                        text = "Ingresar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                }

            }//Column
        }//Box
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioScreenPreview(){
    PantallaInicialScreen (onNavigateToLogin = {})
}