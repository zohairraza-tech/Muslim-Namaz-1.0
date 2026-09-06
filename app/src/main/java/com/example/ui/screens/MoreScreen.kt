package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.UIState

@Composable
fun MoreScreen(
    uiState: UIState,
    onNavigate: (AppDestination) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Islamic Companion Tools",
                subtitle = "More Features & Settings"
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                SectionHeader(title = "Spiritual Resources", subtitle = "Sacred texts and supplications")
            }

            item {
                MoreMenuItem(
                    title = "The Holy Quran",
                    subtitle = "Read 114 Surahs with translation & bookmarks",
                    icon = Icons.Filled.AutoStories,
                    iconColor = Emerald700,
                    onClick = { onNavigate(AppDestination.QuranList) }
                )
            }

            item {
                MoreMenuItem(
                    title = "Authentic Hadith",
                    subtitle = "Bukhari, Muslim, 40 Hadith Nawawi and more",
                    icon = Icons.Filled.FormatQuote,
                    iconColor = Gold700,
                    onClick = { onNavigate(AppDestination.Hadith) }
                )
            }

            item {
                MoreMenuItem(
                    title = "Daily Duas & Supplications",
                    subtitle = "Morning, evening, salah, food, and protection",
                    icon = Icons.Filled.MenuBook,
                    iconColor = Teal700,
                    onClick = { onNavigate(AppDestination.Duas) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(6.dp))
                SectionHeader(title = "Tools & Utilities", subtitle = "Calculators and astronomical converters")
            }

            item {
                MoreMenuItem(
                    title = "Islamic Hijri Calendar",
                    subtitle = "1448 AH calendar, lunar dates, and key Islamic events",
                    icon = Icons.Filled.CalendarMonth,
                    iconColor = Emerald600,
                    onClick = { onNavigate(AppDestination.Calendar) }
                )
            }

            item {
                MoreMenuItem(
                    title = "Zakat Calculator",
                    subtitle = "Calculate 2.5% on cash, gold, silver, and business",
                    icon = Icons.Filled.Calculate,
                    iconColor = Gold600,
                    onClick = { onNavigate(AppDestination.Zakat) }
                )
            }

            item {
                MoreMenuItem(
                    title = "Qibla Compass",
                    subtitle = "Real-time sensor compass pointing to Holy Kaaba",
                    icon = Icons.Filled.Explore,
                    iconColor = Emerald700,
                    onClick = { onNavigate(AppDestination.Qibla) }
                )
            }

            item {
                MoreMenuItem(
                    title = "Digital Tasbeeh",
                    subtitle = "Count dhikr with haptic tap feedback and targets",
                    icon = Icons.Filled.Fingerprint,
                    iconColor = Teal500,
                    onClick = { onNavigate(AppDestination.Tasbeeh) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(6.dp))
                SectionHeader(title = "Configuration", subtitle = "Location and calculation parameters")
            }

            item {
                MoreMenuItem(
                    title = "Change Location",
                    subtitle = "${uiState.selectedCity.name}, ${uiState.selectedCity.country} (GPS or Search)",
                    icon = Icons.Filled.LocationOn,
                    iconColor = MaterialTheme.colorScheme.primary,
                    onClick = { onNavigate(AppDestination.LocationPicker) }
                )
            }

            item {
                MoreMenuItem(
                    title = "Prayer Calculation Settings",
                    subtitle = "${uiState.calculationMethod.displayName} • ${uiState.juristicMethod.displayName}",
                    icon = Icons.Filled.Tune,
                    iconColor = Gold700,
                    onClick = { onNavigate(AppDestination.PrayerSettings) }
                )
            }

            // About Application Card
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Muslim Namaz",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Version 1.0.0 • Pure & Private",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "100% offline astronomical prayer algorithms. No external tracking, no ads, completely privacy-focused for every believer.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MoreMenuItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.14f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
