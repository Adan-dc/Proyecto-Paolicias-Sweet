package com.example.app_en_desarollo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val EsquemaRojo = lightColorScheme(
    primary = Rojo,
    onPrimary = Color.White,
    secondary = RojoClaro,
    onSecondary = VinoTexto,
    tertiary = RojoOscuro,
    onTertiary = Color.White,
    primaryContainer = RosaSuave,
    onPrimaryContainer = VinoTexto,
    secondaryContainer = RosaSuave,
    onSecondaryContainer = VinoTexto
)

@Composable
fun App_En_DesarolloTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> EsquemaRojo
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}