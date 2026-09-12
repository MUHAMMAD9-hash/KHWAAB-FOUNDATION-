package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Luxury Gold Palette
val GoldPrimary = Color(0xFFF5C542)
val GoldSecondary = Color(0xFFD4AF37)
val GoldTertiary = Color(0xFFE6CA65)
val GoldLight = Color(0xFFFFE89E)
val GoldDark = Color(0xFF9E7A1C)
val GoldContainer = Color(0xFF33280B)
val OnGoldContainer = Color(0xFFFFE484)
val GoldBorder = Color(0xFF5A4616)
val GoldBorderLight = Color(0xFF8E712B)

// Deep Black Palette
val PureBlack = Color(0xFF000000)
val DarkCanvas = Color(0xFF0A0A0C)
val DarkSurface = Color(0xFF131316)
val DarkSurfaceVariant = Color(0xFF1B1B20)
val DarkSurfaceContainer = Color(0xFF23232A)
val DarkCardBackground = Color(0xFF16161A)

// Text & Content Accents
val TextPrimaryGold = Color(0xFFFDF7E7)
val TextSecondaryMuted = Color(0xFFA6A298)
val TextTertiaryDark = Color(0xFF706C62)
val AccentGreen = Color(0xFF4CAF50)
val AccentRed = Color(0xFFEF5350)
val AccentOrange = Color(0xFFFF9800)

// Luxury Brushes
val GoldGradientBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFDF74),
        Color(0xFFE5B842),
        Color(0xFFBA8A1F)
    )
)

val DarkSurfaceGradientBrush = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF1A1A20),
        Color(0xFF0D0D10)
    )
)

