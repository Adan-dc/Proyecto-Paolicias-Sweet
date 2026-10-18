package com.matias.paoliciassweet.Componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Footer(
    onInicioClick: () -> Unit,
    onReservaClick: () -> Unit,
    onCatalogoClick: () -> Unit,
    onPerfilClick: () -> Unit,
    copyrightText: String = "© 2026 Paolicias-Sweet. Derechos reservados MAG.",
    backgroundColor: Color = Color(0xFF1B1B1B),
    textColor: Color = Color.Gray
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor),
    ){
        NavigationBar {
            NavigationBarItem(
                selected = false,
                onClick = { onInicioClick },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio"
                    )
                },
                label = {
                    Text("Inicio")
                }
            )
            NavigationBarItem(
                selected = false,
                onClick = { onCatalogoClick() },
                icon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Catalogo"
                    )
                },
                label = {
                    Text("Catalogo")
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = { onReservaClick() },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Reserva"
                    )
                },
                label = {
                    Text("Reserva")
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = { onPerfilClick() },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil"
                    )
                },
                label = {
                    Text("Perfil")
                }
            )
        }

        Text(
            text = copyrightText,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            textAlign = TextAlign.Center
        )

    }
}