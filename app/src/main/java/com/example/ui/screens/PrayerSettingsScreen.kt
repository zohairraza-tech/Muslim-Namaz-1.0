package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.CalculationMethod
import com.example.data.model.JuristicMethod
import com.example.service.AzanPlayer
import com.example.ui.components.AppTopBar
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.MuslimNamazViewModel
import com.example.ui.viewmodel.UIState

@Composable
fun PrayerSettingsScreen(
    uiState: UIState,
    viewModel: MuslimNamazViewModel,
    onNavigate: (AppDestination) -> Unit
) {
    val context = LocalContext.current
    var showMethodDialog by remember { mutableStateOf(false) }
    var showJuristicDialog by remember { mutableStateOf(false) }
    var showAzanDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var isTestingSound by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings & Preferences",
                subtitle = "Calculation, Audio & Display",
                showBackButton = true,
                onBack = { onNavigate(AppDestination.Home) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Section 1: Prayer Calculations
            item {
                SectionHeader(title = "Prayer Calculations", subtitle = "Fajr, Asr and Isha angles")
            }

            // Calculation Method Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showMethodDialog = true },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Calculation Method",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${uiState.calculationMethod.displayName} (Fajr: ${uiState.calculationMethod.fajrAngle}°, Isha: ${uiState.calculationMethod.ishaAngle}°)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = "Change")
                    }
                }
            }

            // Juristic Method Card (Asr)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showJuristicDialog = true },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Asr Juristic Method",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${uiState.juristicMethod.displayName} (${if (uiState.juristicMethod == JuristicMethod.STANDARD) "Shadow 1x object length" else "Shadow 2x object length"})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = "Change")
                    }
                }
            }

            // Section 2: Audio & Notifications
            item {
                Spacer(modifier = Modifier.height(6.dp))
                SectionHeader(title = "Azan & Alerts", subtitle = "Prayer reminder sounds and vibration")
            }

            // Azan Sound Selector
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showAzanDialog = true },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Azan Notification Sound",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = uiState.azanSound,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FilledTonalButton(
                                onClick = {
                                    if (isTestingSound) {
                                        AzanPlayer.stop()
                                        isTestingSound = false
                                    } else {
                                        isTestingSound = true
                                        AzanPlayer.playSoundPreview(context, uiState.azanSound) {
                                            isTestingSound = false
                                        }
                                    }
                                },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = if (isTestingSound) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                                    contentDescription = "Test",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isTestingSound) "Stop" else "Test")
                            }
                        }
                    }
                }
            }

            // Vibration Toggle
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Vibration Alerts",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Vibrate on Azan, Tasbeeh tap and Qibla alignment",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Switch(
                            checked = uiState.isVibrationEnabled,
                            onCheckedChange = { viewModel.setVibrationEnabled(it) }
                        )
                    }
                }
            }

            // Section 3: App Appearance & Language
            item {
                Spacer(modifier = Modifier.height(6.dp))
                SectionHeader(title = "Appearance & General", subtitle = "Theme and display options")
            }

            // Theme Mode
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showThemeDialog = true },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Theme Mode",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = when (uiState.themeMode) {
                                    "LIGHT" -> "Light Emerald"
                                    "DARK" -> "Dark Midnight"
                                    else -> "System Default"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = "Change")
                    }
                }
            }
        }
    }

    // Calculation Method Dialog
    if (showMethodDialog) {
        AlertDialog(
            onDismissRequest = { showMethodDialog = false },
            title = { Text("Select Calculation Method") },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(CalculationMethod.values().toList()) { method ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setCalculationMethod(method)
                                    showMethodDialog = false
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uiState.calculationMethod == method,
                                onClick = {
                                    viewModel.setCalculationMethod(method)
                                    showMethodDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = method.displayName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Fajr: ${method.fajrAngle}°, Isha: ${method.ishaAngle}°",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMethodDialog = false }) { Text("Close") }
            }
        )
    }

    // Juristic Method Dialog
    if (showJuristicDialog) {
        AlertDialog(
            onDismissRequest = { showJuristicDialog = false },
            title = { Text("Select Asr Juristic Method") },
            text = {
                Column {
                    JuristicMethod.values().forEach { method ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setJuristicMethod(method)
                                    showJuristicDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uiState.juristicMethod == method,
                                onClick = {
                                    viewModel.setJuristicMethod(method)
                                    showJuristicDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = method.displayName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (method == JuristicMethod.STANDARD) "Standard (Shafi'i, Maliki, Hanbali)" else "Hanafi School of Thought",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showJuristicDialog = false }) { Text("Close") }
            }
        )
    }

    // Azan Sound Dialog
    if (showAzanDialog) {
        val sounds = listOf("Makkah Azan", "Madinah Azan", "Al-Aqsa Azan", "Gentle Chime", "Beep")
        AlertDialog(
            onDismissRequest = { showAzanDialog = false },
            title = { Text("Select Azan Sound") },
            text = {
                Column {
                    sounds.forEach { sound ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setAzanSound(sound)
                                    showAzanDialog = false
                                    AzanPlayer.playSoundPreview(context, sound)
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uiState.azanSound == sound,
                                onClick = {
                                    viewModel.setAzanSound(sound)
                                    showAzanDialog = false
                                    AzanPlayer.playSoundPreview(context, sound)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = sound, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAzanDialog = false }) { Text("Close") }
            }
        )
    }

    // Theme Dialog
    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text("Choose App Theme") },
            text = {
                Column {
                    listOf("SYSTEM" to "System Default", "LIGHT" to "Light Emerald", "DARK" to "Dark Midnight").forEach { (code, name) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setThemeMode(code)
                                    showThemeDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uiState.themeMode == code,
                                onClick = {
                                    viewModel.setThemeMode(code)
                                    showThemeDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = name, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showThemeDialog = false }) { Text("Close") }
            }
        )
    }
}
