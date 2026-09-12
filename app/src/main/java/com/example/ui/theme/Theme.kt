package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GoldenBlackColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = PureBlack,
    primaryContainer = GoldContainer,
    onPrimaryContainer = OnGoldContainer,
    secondary = GoldSecondary,
    onSecondary = PureBlack,
    secondaryContainer = GoldContainer,
    onSecondaryContainer = GoldLight,
    tertiary = GoldTertiary,
    onTertiary = PureBlack,
    background = DarkCanvas,
    onBackground = TextPrimaryGold,
    surface = DarkSurface,
    onSurface = TextPrimaryGold,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondaryMuted,
    surfaceContainer = DarkSurfaceContainer,
    outline = GoldBorder,
    outlineVariant = GoldBorderLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false, // Keep golden-black brand identity
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = GoldenBlackColorScheme,
        typography = Typography,
        content = content
    )
}

