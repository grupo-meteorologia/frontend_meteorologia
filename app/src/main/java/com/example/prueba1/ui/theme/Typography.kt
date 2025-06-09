package com.example.prueba1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.prueba1.R

// Fuente personalizada
val CalSans = FontFamily(Font(R.font.calsans_regular))

// Estilos tipográficos con CalSans
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = CalSans,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = CalSans,
        fontSize = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = CalSans,
        fontSize = 14.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = CalSans,
        fontSize = 28.sp
    )
)
