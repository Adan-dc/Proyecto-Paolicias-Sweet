package com.example.app_en_desarollo.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app_en_desarollo.R
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import java.io.ByteArrayOutputStream

@Composable
fun ImagenProducto(
    imagenUrl: String?,
    descripcion: String?,
    fallback: Int,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    if (imagenUrl != null && imagenUrl.startsWith("imagen-")) {
        AsyncImage(
            model = RetrofitGeneral.urlImagen(imagenUrl),
            contentDescription = descripcion,
            contentScale = contentScale,
            placeholder = painterResource(id = fallback),
            error = painterResource(id = fallback),
            modifier = modifier
        )
    } else {
        Image(
            painter = painterResource(id = fallback),
            contentDescription = descripcion,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

fun leerImagenComprimida(context: Context, uri: Uri): ByteArray? {
    return try {
        val original = context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it)
        } ?: return null

        val lado = maxOf(original.width, original.height)
        val escala = if (lado > 1280) 1280f / lado else 1f
        val reducida = if (escala < 1f) {
            Bitmap.createScaledBitmap(
                original,
                (original.width * escala).toInt(),
                (original.height * escala).toInt(),
                true
            )
        } else {
            original
        }

        val salida = ByteArrayOutputStream()
        reducida.compress(Bitmap.CompressFormat.JPEG, 85, salida)
        salida.toByteArray()
    } catch (e: Exception) {
        null
    }
}

@Composable
fun SelectorFoto(
    imagenActual: String?,
    fotoBytes: ByteArray?,
    onFotoElegida: (ByteArray) -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val bytes = leerImagenComprimida(context, uri)
            if (bytes != null) {
                onFotoElegida(bytes)
            }
        }
    }

    val vistaPrevia = remember(fotoBytes) {
        fotoBytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size)?.asImageBitmap() }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (vistaPrevia != null) {
            Image(
                bitmap = vistaPrevia,
                contentDescription = "Foto elegida",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        } else {
            ImagenProducto(
                imagenUrl = imagenActual,
                descripcion = "Foto actual",
                fallback = R.drawable.imagenmenu,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(onClick = { launcher.launch("image/*") }) {
            Text(if (fotoBytes != null || imagenActual != null) "Cambiar foto" else "Elegir foto")
        }
    }
}
fun drawableLocal(nombre: String?): Int {
    return when (nombre?.substringBeforeLast(".")) {
        "tartadefresasyvainilla" -> R.drawable.tartadefresasyvainilla
        "cookiesdechocolate" -> R.drawable.cookiesdechocolate
        "bizcochodelimon" -> R.drawable.bizcochodelimon
        "browniedechocolate" -> R.drawable.browniedechocolate
        "cheesecakedeframbuesa" -> R.drawable.cheesecakedeframbuesa
        "rollitosdecanela" -> R.drawable.rollitosdecanela
        "ensaladamixdeverduras" -> R.drawable.ensaladamixdeverduras
        "ensaladacesarconpollo" -> R.drawable.ensaladacesarconpollo
        "ensaladachilena" -> R.drawable.ensaladachilena
        "ensaladadetomateconzanahorias" -> R.drawable.ensaladadetomateconzanahorias
        "ensaladagourmetcontrufas" -> R.drawable.ensaladagourmetcontrufas
        "ensaladadecaviar" -> R.drawable.ensaladadecaviar
        "bruschettasdetomate" -> R.drawable.bruschettasdetomate
        "polloalhornoconpapas" -> R.drawable.polloalhornoconpapas
        "limonadaconmenta" -> R.drawable.limonadaconmenta
        "jugodefrutilla" -> R.drawable.jugodefrutilla
        "jugodenaranja" -> R.drawable.jugodenaranja
        "cafelatte" -> R.drawable.cafelatte
        "packestandar" -> R.drawable.packestandar
        "packpremium" -> R.drawable.packpremium
        "packvegetariano" -> R.drawable.packvegetariano
        "promocumpleanos" -> R.drawable.promocumpleanos
        else -> R.drawable.emblema_paolicias_sweet
    }
}