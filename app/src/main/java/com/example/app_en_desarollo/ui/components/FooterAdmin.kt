
package com.example.app_en_desarollo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val GranateFooter = Color(0xFFA3375D)

@Composable
fun FooterAdmin(
    seccionActual: String,
    onCalendarioClick: () -> Unit,
    onProductosClick: () -> Unit,
    onCatalogoClick: () -> Unit,
    onReservasClick: () -> Unit,
    onPersonalClick: () -> Unit,
    onPerfilClick: () -> Unit
) {
    val opciones = listOf(
        Triple("Calendario", Icons.Default.DateRange, onCalendarioClick),
        Triple("Productos", Icons.Default.Apps, onProductosClick),
        Triple("Catálogo", Icons.Default.Devices, onCatalogoClick),
        Triple("Reservas", Icons.Default.AddCard, onReservasClick),
        Triple("Personal", Icons.Default.Diversity3, onPersonalClick),
        Triple("Perfil", Icons.Default.Person, onPerfilClick)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1B1B1B))
    ) {
        NavigationBar(
            containerColor = Color(0xFFF4EDF7)
        ) {
            opciones.forEach { (nombre, icono, accion) ->
                NavigationBarItem(
                    selected = seccionActual == nombre,
                    onClick = accion,
                    icon = {
                        Icon(
                            imageVector = icono,
                            contentDescription = nombre
                        )
                    },
                    label = {
                        Text(
                            text = nombre,
                            fontSize = 9.sp,
                            maxLines = 1
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GranateFooter,
                        selectedTextColor = GranateFooter,
                        indicatorColor = Color(0xFFF7DCE5),
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray
                    )
                )
            }
        }

        Text(
            text = "© 2026 PaoliciasSweet. Derechos reservados MAG.",
            color = Color.LightGray,
            fontSize = 9.sp,
            maxLines = 1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
    }
}
