package com.example.app_en_desarollo.ui.screen
import com.example.app_en_desarollo.ui.components.drawableLocal
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.R
import com.example.app_en_desarollo.model.PackResponse
import com.example.app_en_desarollo.model.ProductoResponse
import com.example.app_en_desarollo.ui.components.ImagenProducto
import com.example.app_en_desarollo.ui.components.SelectorFoto
import com.example.app_en_desarollo.ui.viewmodel.AdminPacksViewModel
import com.example.app_en_desarollo.ui.viewmodel.ProductoSeleccionado
import java.util.Locale

private val Morado = Color(0xFFA63A5B)

private fun formatoPrecio(precio: Int): String {
    return "$" + String.format(Locale.US, "%,d", precio).replace(",", ".")
}

@Composable
fun AdminPacksScreen(viewModel: AdminPacksViewModel = viewModel()) {
    val context = LocalContext.current
    var mostrarFormulario by remember { mutableStateOf(false) }
    var packEditando by remember { mutableStateOf<PackResponse?>(null) }
    var packAEliminar by remember { mutableStateOf<PackResponse?>(null) }

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
                Text(text = "Packs", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "${viewModel.packs.size} registrados",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.packs, key = { it.id }) { pack ->
                    TarjetaPack(
                        pack = pack,
                        onEditar = {
                            packEditando = pack
                            mostrarFormulario = true
                        },
                        onEliminar = { packAEliminar = pack },
                        onCambiarActivo = { viewModel.cambiarActivo(pack, it) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = {
                packEditando = null
                mostrarFormulario = true
            },
            containerColor = Morado,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar")
        }
    }

    if (mostrarFormulario) {
        FormularioPack(
            pack = packEditando,
            productosDisponibles = viewModel.productosDisponibles,
            onCancelar = { mostrarFormulario = false },
            onGuardar = { nombre, precio, tipo, personas, activo, productos, foto ->
                viewModel.guardar(
                    id = packEditando?.id,
                    nombre = nombre,
                    precio = precio,
                    tipo = tipo,
                    personas = personas,
                    activo = activo,
                    imagenActual = packEditando?.imagenUrl,
                    imagenBytes = foto,
                    productos = productos,
                    packOriginal = packEditando,
                    onListo = { mostrarFormulario = false }
                )
            }
        )
    }

    packAEliminar?.let { pack ->
        AlertDialog(
            onDismissRequest = { packAEliminar = null },
            title = { Text("Eliminar pack") },
            text = { Text("¿Seguro que quieres eliminar \"${pack.nombre}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.eliminar(pack.id)
                        packAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { packAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun TarjetaPack(
    pack: PackResponse,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    onCambiarActivo: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImagenProducto(
                imagenUrl = pack.imagenUrl,
                descripcion = pack.nombre,
                fallback = drawableLocal(pack.imagenUrl),
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(text = pack.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    text = formatoPrecio(pack.precio),
                    color = Morado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "${pack.personas ?: 0} personas - ${pack.tipo ?: ""}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = "${pack.productos.orEmpty().size} productos incluidos",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Activo", fontSize = 12.sp)
                    Spacer(modifier = Modifier.size(6.dp))
                    Switch(
                        checked = pack.activo != false,
                        onCheckedChange = onCambiarActivo
                    )
                }
            }

            Column {
                IconButton(onClick = onEditar) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Morado)
                }
                IconButton(onClick = onEliminar) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFD32F2F)
                    )
                }
            }
        }
    }
}

@Composable
private fun FormularioPack(
    pack: PackResponse?,
    productosDisponibles: List<ProductoResponse>,
    onCancelar: () -> Unit,
    onGuardar: (String, Int, String, Int?, Boolean, List<ProductoSeleccionado>, ByteArray?) -> Unit
) {
    val tipos: List<String> = listOf("ESTANDAR", "PREMIUM", "VEGETARIANO", "PROMO")
    val grupos: List<String> = listOf("Dulces", "Salados", "Bebestibles")

    var nombre by remember(pack) { mutableStateOf(pack?.nombre ?: "") }
    var precio by remember(pack) { mutableStateOf(pack?.precio?.toString() ?: "") }
    var personas by remember(pack) { mutableStateOf(pack?.personas?.toString() ?: "") }
    var tipo by remember(pack) { mutableStateOf(pack?.tipo ?: "ESTANDAR") }
    var activo by remember(pack) { mutableStateOf(pack?.activo != false) }
    var seleccionados by remember(pack) {
        mutableStateOf<List<ProductoSeleccionado>>(
            pack?.productos.orEmpty().mapNotNull { pp ->
                val p = pp.producto
                if (p == null) {
                    null
                } else {
                    ProductoSeleccionado(
                        id = p.id,
                        nombre = p.nombre,
                        cantidad = pp.cantidad,
                        grupo = pp.grupo ?: "Dulces"
                    )
                }
            }
        )
    }
    var productoElegido by remember { mutableStateOf<ProductoResponse?>(null) }
    var grupoElegido by remember { mutableStateOf("Dulces") }
    var cantidadTexto by remember { mutableStateOf("") }
    var menuTipo by remember { mutableStateOf(false) }
    var menuProducto by remember { mutableStateOf(false) }
    var menuGrupo by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var fotoBytes by remember { mutableStateOf<ByteArray?>(null) }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(if (pack == null) "Nuevo pack" else "Editar pack") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it.filter { c -> c.isDigit() } },
                    label = { Text("Precio") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = personas,
                    onValueChange = { personas = it.filter { c -> c.isDigit() } },
                    label = { Text("Personas") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Box {
                    OutlinedButton(
                        onClick = { menuTipo = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tipo: $tipo")
                    }
                    DropdownMenu(
                        expanded = menuTipo,
                        onDismissRequest = { menuTipo = false }
                    ) {
                        tipos.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    tipo = opcion
                                    menuTipo = false
                                }
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Activo", modifier = Modifier.weight(1f))
                    Switch(checked = activo, onCheckedChange = { activo = it })
                }

                SelectorFoto(
                    imagenActual = pack?.imagenUrl,
                    fotoBytes = fotoBytes,
                    onFotoElegida = { fotoBytes = it }
                )

                Text(
                    text = "Productos incluidos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                if (seleccionados.isEmpty()) {
                    Text(text = "Sin productos agregados", color = Color.Gray, fontSize = 13.sp)
                }

                seleccionados.forEach { item ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${item.nombre} x${item.cantidad} (${item.grupo})",
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { seleccionados = seleccionados.filter { it.id != item.id } }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Quitar",
                                tint = Color(0xFFD32F2F)
                            )
                        }
                    }
                }

                Box {
                    OutlinedButton(
                        onClick = { menuProducto = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(productoElegido?.nombre ?: "Elegir producto")
                    }
                    DropdownMenu(
                        expanded = menuProducto,
                        onDismissRequest = { menuProducto = false }
                    ) {
                        productosDisponibles.forEach { producto ->
                            DropdownMenuItem(
                                text = { Text(producto.nombre) },
                                onClick = {
                                    productoElegido = producto
                                    menuProducto = false
                                    error = null
                                }
                            )
                        }
                    }
                }

                Box {
                    OutlinedButton(
                        onClick = { menuGrupo = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Grupo: $grupoElegido")
                    }
                    DropdownMenu(
                        expanded = menuGrupo,
                        onDismissRequest = { menuGrupo = false }
                    ) {
                        grupos.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    grupoElegido = opcion
                                    menuGrupo = false
                                }
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = cantidadTexto,
                        onValueChange = { cantidadTexto = it.filter { c -> c.isDigit() } },
                        label = { Text("Cantidad") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Button(
                        onClick = {
                            val elegido = productoElegido
                            val cantidad = cantidadTexto.toIntOrNull()
                            if (elegido == null) {
                                error = "Elige un producto"
                            } else if (cantidad == null || cantidad <= 0) {
                                error = "Ingresa una cantidad valida"
                            } else {
                                error = null
                                seleccionados = seleccionados.filter { it.id != elegido.id } +
                                        ProductoSeleccionado(
                                            id = elegido.id,
                                            nombre = elegido.nombre,
                                            cantidad = cantidad,
                                            grupo = grupoElegido
                                        )
                                productoElegido = null
                                cantidadTexto = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Morado)
                    ) {
                        Text("Agregar")
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
                    val precioInt = precio.toIntOrNull()
                    when {
                        nombre.isBlank() -> error = "Debe ingresar el nombre"
                        precioInt == null || precioInt <= 0 -> error = "Debe ingresar un precio valido"
                        else -> {
                            error = null
                            onGuardar(
                                nombre.trim(),
                                precioInt,
                                tipo,
                                personas.toIntOrNull(),
                                activo,
                                seleccionados,
                                fotoBytes
                            )
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Morado)
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