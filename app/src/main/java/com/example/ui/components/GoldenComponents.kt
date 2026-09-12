package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkCardBackground
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldBorderLight
import com.example.ui.theme.GoldGradientBrush
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.PureBlack
import com.example.ui.theme.TextPrimaryGold
import com.example.ui.theme.TextSecondaryMuted

@Composable
fun GoldCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    borderWidth: Float = 1f,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(borderWidth.dp, GoldBorder),
                shape = shape
            ),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = DarkCardBackground
        )
    ) {
        content()
    }
}

@Composable
fun GoldBadge(
    text: String,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = false,
    icon: ImageVector? = null
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(20.dp)),
        color = if (isPrimary) GoldPrimary else DarkSurfaceVariant,
        border = if (isPrimary) null else BorderStroke(1.dp, GoldBorderLight)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isPrimary) PureBlack else GoldPrimary,
                    modifier = Modifier
                        .size(12.dp)
                        .padding(end = 4.dp)
                )
            }
            Text(
                text = text,
                color = if (isPrimary) PureBlack else GoldLight,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun GoldProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(DarkSurfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(GoldGradientBrush)
        )
    }
}

@Composable
fun GoldButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldPrimary,
            contentColor = PureBlack,
            disabledContainerColor = DarkSurfaceVariant,
            disabledContentColor = TextSecondaryMuted
        ),
        modifier = modifier.height(44.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun GoldOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, GoldPrimary),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = GoldLight
        ),
        modifier = modifier.height(44.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
    }
}

@Composable
fun SuratCenterNoticeBanner(
    onInfoClick: () -> Unit,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GoldCard(
        modifier = modifier.testTag("surat_identity_banner")
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(GoldPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Education Centre",
                        tint = PureBlack,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Khwaab Foundation, Surat",
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Education Centre • Sayedpura, Surat",
                        color = TextSecondaryMuted,
                        fontSize = 11.5.sp,
                        maxLines = 1
                    )
                }

                IconButton(
                    onClick = onCallClick,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("call_surat_foundation_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call Centre",
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onInfoClick,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("info_surat_foundation_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "About Surat Centre",
                        tint = GoldSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                color = Color(0x22F5C542),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(0.5.dp, GoldBorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "2nd Floor, Pumping Char Rasta, above Mubin Cycle Wala, Navi Chal, Sayedpura, Surat",
                        color = GoldLight,
                        fontSize = 11.sp,
                        lineHeight = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SuratFoundationInfoDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardBackground,
        titleContentColor = GoldPrimary,
        textContentColor = TextPrimaryGold,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Khwaab Foundation (Surat)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "About the Surat Organization:",
                    fontWeight = FontWeight.SemiBold,
                    color = GoldLight,
                    fontSize = 13.sp
                )
                Text(
                    text = "Khwaab Foundation in Surat is an education-focused organization and learning centre committed to student academic empowerment and teaching excellence.",
                    fontSize = 12.5.sp,
                    color = TextPrimaryGold,
                    lineHeight = 17.sp
                )

                Surface(
                    color = DarkSurfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, GoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "📍 Sayedpura Education Centre:",
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "2nd Floor, Pumping Char Rasta, above Mubin Cycle Wala, Navi Chal, Sayedpura, Surat, Gujarat.",
                            color = TextPrimaryGold,
                            fontSize = 11.5.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                Surface(
                    color = DarkSurfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, GoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "💼 Teaching & Faculty Opportunities:",
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Active recruitment for educators including Hindi-teaching roles, both full-time and part-time educator positions.",
                            color = TextPrimaryGold,
                            fontSize = 11.5.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                Text(
                    text = "Important Note: Please do not mix this Surat organization with other organizations sharing the name across India.",
                    fontSize = 11.sp,
                    color = TextSecondaryMuted,
                    lineHeight = 15.sp
                )
            }
        },
        confirmButton = {
            GoldButton(
                text = "Call (+91 74050 68691)",
                icon = Icons.Default.Call,
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:+917405068691")
                    }
                    context.startActivity(intent)
                }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = TextSecondaryMuted)
            }
        }
    )
}
