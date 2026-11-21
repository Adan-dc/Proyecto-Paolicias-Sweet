package com.example.app_en_desarollo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.model.IngredienteResponse
import com.example.app_en_desarollo.ui.viewmodel.AdminIngredientesViewModel
import java.util.Locale

private val MoradoIng = Color(0xFFA63A5B)

private val unidadesComunes = listOf("kg", "g", "litro", "ml", "unidad", "docena")

private fun textoNumero(valor: Double): String {
    return if (valor % 1.0 == 0.0) valor.toLong().toString() else valor.toString()
}

private fun textoPrecio(valor: Double): String {
    val formato = if (valor % 1.0 == 0.0) "%,.0f" else "%,.2f"
    return "$" + String.format(Locale.US, formato, valor).replace(",", ".")
}

@Composable
fun AdminIngredientesScreen(
    viewModel: AdminIngredientesViewModel = viewModel()
) {
    val context = LocalContext.current
    var mostrarFormulario by remember { mutableStateOf(false) }
    var ingredienteEditando by remember { mutableStateOf<IngredienteResponse?>(null) }
    var ingredienteAEliminar by remember { mutableStateOf<IngredienteResponse?>(null) }

    LaunchedEffect(Unit) {
        viewModel.cargar()
    }

    LaunchedEffect(viewModel.mensaje) {
        viewModel.mensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.limpiarMensaje()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ingredientes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${viewModel.ingredientes.size} registrados",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.ingredientes, key = { it.id }) { ingrediente ->
                    TarjetaIngrediente(
                        ingrediente = ingrediente,
                        onEditar = {
                            ingredienteEditando = ingrediente
                            mostrarFormulario = true
                        },
                        onEliminar = { ingredienteAEliminar = ingrediente }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = {
                ingredienteEditando = null
                mostrarFormulario = true
            },
            containerColor = MoradoIng,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar")
        }
    }

    if (mostrarFormulario) {
        FormularioIngrediente(
            ingrediente = ingredienteEditando,
            onCancelar = { mostrarFormulario = false },
            onGuardar = { nombre, precio, unidad ->
                viewModel.guardar(
                    id = ingredienteEditando?.id,
                    nombre = nombre,
                    precioUnidad = precio,
                    unidadMedida = unidad,
                    onListo = { mostrarFormulario = false }
                )
            }
        )
    }

    ingredienteAEliminar?.let { ingrediente ->
        AlertDialog(
            onDismissRequest = { ingredienteAEliminar = null },
            title = { Text("Eliminar ingrediente") },
            text = { Text("¿Seguro que quieres eliminar \"${ingrediente.nombre}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.eliminar(ingrediente.id)
                        ingredienteAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { ingredienteAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun TarjetaIngrediente(
    ingrediente: IngredienteResponse,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    val detalle = if (ingrediente.precioUnidad != null) {
        "${textoPrecio(ingrediente.precioUnidad)} / ${ingrediente.unidadMedida ?: ""}"
    } else {
        "Sin precio"
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = ingrediente.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = detalle,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MoradoIng)
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFFD32F2F))
            }
        }
    }
}

@Composable
private fun FormularioIngrediente(
    ingrediente: IngredienteResponse?,
    onCancelar: () -> Unit,
    onGuardar: (String, Double, String) -> Unit
) {
    var nombre by remember(ingrediente) { mutableStateOf(ingrediente?.nombre ?: "") }
    var precio by remember(ingrediente) {
        mutableStateOf(ingrediente?.precioUnidad?.let { textoNumero(it) } ?: "")
    }
    var unidad by remember(ingrediente) { mutableStateOf(ingrediente?.unidadMedida ?: "") }
    var menuAbierto by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(if (ingrediente == null) "Nuevo ingrediente" else "Editar ingrediente") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Precio por unidad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                Box {
                    OutlinedButton(
                        onClick = { menuAbierto = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (unidad.isBlank()) "Unidad de medida" else unidad)
                    }

                    DropdownMenu(
                        expanded = menuAbierto,
                        onDismissRequest = { menuAbierto = false }
                    ) {
                        unidadesComunes.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    unidad = opcion
                                    menuAbierto = false
                                    error = null
                                }
                            )
                        }
                    }
                }

                error?.let {
                    Text(text = it, color = Color(0xFFD32F2F), fontSize = 13.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val precioDouble = precio.toDoubleOrNull()
                    when {
                        nombre.isBlank() -> error = "Debe ingresar el nombre"
                        precioDouble == null || precioDouble <= 0 -> error = "Debe ingresar un precio valido"
                        unidad.isBlank() -> error = "Debe seleccionar la unidad de medida"
                        else -> {
                            error = null
                            onGuardar(nombre.trim(), precioDouble, unidad)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MoradoIng)
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}