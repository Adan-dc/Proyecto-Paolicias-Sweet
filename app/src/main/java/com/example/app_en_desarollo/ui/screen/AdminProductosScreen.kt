package com.example.app_en_desarollo.ui.screen
import com.example.app_en_desarollo.ui.components.drawableLocal
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.R
import com.example.app_en_desarollo.model.CategoriaResponse
import com.example.app_en_desarollo.model.IngredienteResponse
import com.example.app_en_desarollo.model.ProductoResponse
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.components.ImagenProducto
import com.example.app_en_desarollo.ui.components.SelectorFoto
import com.example.app_en_desarollo.ui.viewmodel.AdminProductosViewModel
import com.example.app_en_desarollo.ui.viewmodel.IngredienteSeleccionado
import java.util.Locale
private val Morado = Color(0xFFA63A5B)
private val Rosado = Color(0xFFFFC5D3)

private fun formatoPrecio(precio: Int): String {
    return "$" + String.format(Locale.US, "%,d", precio).replace(",", ".")
}

private fun textoCantidad(valor: Double): String {
    return if (valor % 1.0 == 0.0) valor.toLong().toString() else valor.toString()
}

@Composable
fun AdminProductosScreen(
    bottomBar: @Composable () -> Unit = {},
    viewModel: AdminProductosViewModel = viewModel()
) {
    var pestana by remember { mutableStateOf("Productos") }
    var seccion by remember { mutableStateOf("Todas") }
    var mostrarFormulario by remember { mutableStateOf(false) }
    var productoEditando by remember { mutableStateOf<ProductoResponse?>(null) }
    var productoAEliminar by remember { mutableStateOf<ProductoResponse?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(pestana) {
        viewModel.cargar()
    }

    LaunchedEffect(viewModel.mensaje) {
        viewModel.mensaje?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.limpiarMensaje()
        }
    }

    val filtrados = viewModel.productos.filter {
        seccion == "Todas" || it.categoria?.seccion == seccion
    }

    Scaffold(
        topBar = { Header(title = "ADMINISTRACIÓN") },
        bottomBar = bottomBar,
        containerColor = Rosado,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (pestana == "Productos") {
                FloatingActionButton(
                    onClick = {
                        productoEditando = null
                        mostrarFormulario = true
                    },
                    containerColor = Morado,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Productos", "Ingredientes", "Packs").forEach { nombre ->
                    BotonSeleccion(
                        texto = nombre,
                        activo = pestana == nombre,
                        onClick = { pestana = nombre },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (pestana == "Productos") {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Todas", "Menu", "Reposteria", "Bebestibles").forEach { nombre ->
                        BotonSeleccion(
                            texto = nombre,
                            activo = seccion == nombre,
                            onClick = { seccion = nombre },
                            modifier = Modifier
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Productos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${filtrados.size} registrados",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filtrados, key = { it.id }) { producto ->
                        TarjetaProducto(
                            producto = producto,
                            onEditar = {
                                productoEditando = producto
                                mostrarFormulario = true
                            },
                            onEliminar = { productoAEliminar = producto }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            } else if (pestana == "Ingredientes") {
                AdminIngredientesScreen()
            } else{
                AdminPacksScreen()
            }
        }
    }

    if (mostrarFormulario) {
        FormularioProducto(
            producto = productoEditando,
            categorias = viewModel.categorias,
            ingredientesDisponibles = viewModel.ingredientesDisponibles,
            onCancelar = { mostrarFormulario = false },
            onGuardar = { nombre, precio, descripcion, categoriaId, ingredientes, foto ->
                viewModel.guardar(
                    id = productoEditando?.id,
                    nombre = nombre,
                    precioBase = precio,
                    descripcion = descripcion,
                    imagenUrl = productoEditando?.imagenUrl,
                    imagenBytes = foto,
                    categoriaId = categoriaId,
                    ingredientes = ingredientes,
                    productoOriginal = productoEditando,
                    onListo = { mostrarFormulario = false }
                )
            }
        )
    }

    productoAEliminar?.let { producto ->
        AlertDialog(
            onDismissRequest = { productoAEliminar = null },
            title = { Text("Eliminar producto") },
            text = { Text("¿Seguro que quieres eliminar \"${producto.nombre}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.eliminar(producto.id)
                        productoAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { productoAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun BotonSeleccion(
    texto: String,
    activo: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (activo) Color(0xFF6E1F3A) else Color(0xFFA63A5B),
            contentColor = Color.White
        )
    ) {
        Text(text = texto, fontSize = 13.sp, maxLines = 1)
    }
}

@Composable
private fun TarjetaProducto(
    producto: ProductoResponse,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
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
                imagenUrl = producto.imagenUrl,
                descripcion = producto.nombre,
                fallback = drawableLocal(producto.imagenUrl),
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = formatoPrecio(producto.precioBase),
                    color = Morado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = producto.categoria?.nombre ?: "Sin categoría",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Morado)
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFFD32F2F))
            }
        }
    }
}

@Composable
private fun FormularioProducto(
    producto: ProductoResponse?,
    categorias: List<CategoriaResponse>,
    ingredientesDisponibles: List<IngredienteResponse>,
    onCancelar: () -> Unit,
    onGuardar: (String, Int, String, Long, List<IngredienteSeleccionado>, ByteArray?) -> Unit
) {
    var nombre by remember(producto) { mutableStateOf(producto?.nombre ?: "") }
    var precio by remember(producto) { mutableStateOf(producto?.precioBase?.toString() ?: "") }
    var descripcion by remember(producto) { mutableStateOf(producto?.descripcion ?: "") }
    var categoriaId by remember(producto) { mutableStateOf(producto?.categoria?.id) }
    var seleccionados by remember(producto) {
        mutableStateOf<List<IngredienteSeleccionado>>(
            producto?.ingredientes?.map { pi ->
                IngredienteSeleccionado(
                    id = pi.ingrediente.id,
                    nombre = pi.ingrediente.nombre,
                    unidad = pi.ingrediente.unidadMedida,
                    cantidad = pi.cantidad
                )
            } ?: emptyList()
        )
    }
    var ingredienteElegido by remember { mutableStateOf<IngredienteResponse?>(null) }
    var cantidadTexto by remember { mutableStateOf("") }
    var menuCategoria by remember { mutableStateOf(false) }
    var menuIngrediente by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var fotoBytes by remember { mutableStateOf<ByteArray?>(null) }

    val categoriaSeleccionada = categorias.find { it.id == categoriaId }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(if (producto == null) "Nuevo producto" else "Editar producto") },
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
                    label = { Text("Precio base") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                Box {
                    OutlinedButton(
                        onClick = { menuCategoria = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            categoriaSeleccionada?.let { "${it.seccion} - ${it.nombre}" }
                                ?: "Selecciona una categoría"
                        )
                    }

                    DropdownMenu(
                        expanded = menuCategoria,
                        onDismissRequest = { menuCategoria = false }
                    ) {
                        categorias.forEach { categoria ->
                            DropdownMenuItem(
                                text = { Text("${categoria.seccion} - ${categoria.nombre}") },
                                onClick = {
                                    categoriaId = categoria.id
                                    menuCategoria = false
                                    error = null
                                }
                            )
                        }
                    }
                }

                SelectorFoto(
                    imagenActual = producto?.imagenUrl,
                    fotoBytes = fotoBytes,
                    onFotoElegida = { fotoBytes = it }
                )

                Text(
                    text = "Ingredientes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                if (seleccionados.isEmpty()) {
                    Text(text = "Sin ingredientes agregados", color = Color.Gray, fontSize = 13.sp)
                }

                seleccionados.forEach { item ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${item.nombre}: ${textoCantidad(item.cantidad)} ${item.unidad ?: ""}",
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
                        onClick = { menuIngrediente = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(ingredienteElegido?.nombre ?: "Elegir ingrediente")
                    }

                    DropdownMenu(
                        expanded = menuIngrediente,
                        onDismissRequest = { menuIngrediente = false }
                    ) {
                        ingredientesDisponibles.forEach { ingrediente ->
                            DropdownMenuItem(
                                text = { Text(ingrediente.nombre) },
                                onClick = {
                                    ingredienteElegido = ingrediente
                                    menuIngrediente = false
                                    error = null
                                }
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = cantidadTexto,
                        onValueChange = { cantidadTexto = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Cantidad ${ingredienteElegido?.unidadMedida ?: ""}") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Button(
                        onClick = {
                            val elegido = ingredienteElegido
                            val cantidad = cantidadTexto.toDoubleOrNull()
                            if (elegido == null) {
                                error = "Elige un ingrediente"
                            } else if (cantidad == null || cantidad <= 0) {
                                error = "Ingresa una cantidad valida"
                            } else {
                                error = null
                                seleccionados = seleccionados.filter { it.id != elegido.id } +
                                        IngredienteSeleccionado(
                                            id = elegido.id,
                                            nombre = elegido.nombre,
                                            unidad = elegido.unidadMedida,
                                            cantidad = cantidad
                                        )
                                ingredienteElegido = null
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
                        categoriaId == null -> error = "Debe seleccionar una categoría"
                        else -> {
                            error = null
                            onGuardar(
                                nombre.trim(),
                                precioInt,
                                descripcion.trim(),
                                categoriaId!!,
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