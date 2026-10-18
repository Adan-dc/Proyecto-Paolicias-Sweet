package com.matias.paoliciassweet.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFA63A5B),
    onPrimary = Color.White,
    secondary = Color(0xFF5A1F33),
    tertiary = Color(0xFFF4CFD7),
    background = Color(0xFFFBE9EC),
    onBackground = Color(0xFF5A1F33),
    surface = Color(0xFFFFF8F6),
    onSurface = Color(0xFF5A1F33)
)

@Composable
fun PaoliciasSweetTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}