package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CalculationMethod
import com.example.data.model.JuristicMethod
import com.example.data.model.PrayerName
import com.example.service.AzanPlayer
import com.example.ui.components.AppTopBar
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.MuslimNamazViewModel
import com.example.ui.viewmodel.UIState

@Composable
fun NamazScreen(
    uiState: UIState,
    viewModel: MuslimNamazViewModel,
    onNavigate: (AppDestination) -> Unit
) {
    val context = LocalContext.current
    var showSoundDialog by remember { mutableStateOf(false) }
    var isPlayingPreview by remember { mutableStateOf(false) }

    val schedule = uiState.todaySchedule
    val completedCount = uiState.completedPrayersToday.size
    val totalObligatory = 5
    val progressFraction = (completedCount.toFloat() / totalObligatory).coerceIn(0f, 1f)

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Prayer Times",
                subtitle = "${uiState.selectedCity.name} • ${uiState.calculationMethod.displayName}",
                actions = {
                    IconButton(onClick = { onNavigate(AppDestination.PrayerSettings) }) {
                        Icon(
                            imageVector = Icons.Outlined.Tune,
                            contentDescription = "Prayer Settings",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
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
            // 1. Daily Progress & Streak Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Daily Salah Tracking",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "$completedCount of $totalObligatory prayers completed today",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$completedCount/$totalObligatory",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = Gold600,
                            trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f)
                        )
                    }
                }
            }

            // 2. Azan & Notification Controls Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Azan Tone: ${uiState.azanSound}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (uiState.isVibrationEnabled) "Vibration: ON" else "Silent / Muted",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FilledTonalButton(
                                onClick = {
                                    if (isPlayingPreview) {
                                        AzanPlayer.stop()
                                        isPlayingPreview = false
                                    } else {
                                        isPlayingPreview = true
                                        AzanPlayer.playSoundPreview(context, uiState.azanSound) {
                                            isPlayingPreview = false
                                        }
                                    }
                                },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = if (isPlayingPreview) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                                    contentDescription = "Test Azan",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isPlayingPreview) "Stop" else "Test")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            OutlinedButton(
                                onClick = { showSoundDialog = true },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Change")
                            }
                        }
                    }
                }
            }

            // 3. Prayer Times List
            if (schedule != null) {
                val prayerList = listOf(
                    PrayerName.FAJR to schedule.fajr,
                    PrayerName.SUNRISE to schedule.sunrise,
                    PrayerName.DHUHR to schedule.dhuhr,
                    PrayerName.ASR to schedule.asr,
                    PrayerName.MAGHRIB to schedule.maghrib,
                    PrayerName.ISHA to schedule.isha
                )

                items(prayerList) { (prayer, time) ->
                    val isNext = uiState.nextPrayerInfo?.prayerName == prayer
                    val isCompleted = uiState.completedPrayersToday.contains(prayer.name)
                    var notifEnabled by remember(prayer) {
                        mutableStateOf(viewModel.isPrayerNotificationEnabled(prayer))
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (isNext) Modifier.border(
                                    1.5.dp,
                                    Gold600,
                                    RoundedCornerShape(18.dp)
                                ) else Modifier
                            ),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isNext) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                            else MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (isNext) 3.dp else 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left: Prayer Icon & Details
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isNext) Gold500.copy(alpha = 0.2f)
                                            else MaterialTheme.colorScheme.surfaceVariant
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = when (prayer) {
                                            PrayerName.FAJR -> Icons.Filled.NightsStay
                                            PrayerName.SUNRISE -> Icons.Filled.WbSunny
                                            PrayerName.DHUHR -> Icons.Filled.BrightnessHigh
                                            PrayerName.ASR -> Icons.Filled.BrightnessMedium
                                            PrayerName.MAGHRIB -> Icons.Filled.WbTwilight
                                            PrayerName.ISHA -> Icons.Filled.Bedtime
                                        },
                                        contentDescription = prayer.englishName,
                                        tint = if (isNext) Gold700 else MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = prayer.englishName,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        if (isNext) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(Gold600)
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = "NEXT",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = PureWhite
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        text = prayer.arabicName,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            // Right: Time + Notif Switch + Completion Checkbox
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = time,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isNext) Gold700 else MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                // Azan alarm switch
                                Switch(
                                    checked = notifEnabled,
                                    onCheckedChange = { checked ->
                                        notifEnabled = checked
                                        viewModel.setPrayerNotification(prayer, checked)
                                    },
                                    modifier = Modifier.size(36.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                // Prayer completed checkbox (only for obligatory 5 prayers)
                                if (prayer.isObligatoryPrayer) {
                                    IconButton(
                                        onClick = { viewModel.togglePrayerCompletion(prayer) },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isCompleted) Icons.Filled.CheckCircle
                                            else Icons.Outlined.CheckCircle,
                                            contentDescription = "Mark as prayed",
                                            tint = if (isCompleted) Emerald600
                                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. Juristic and Calculation Settings Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(AppDestination.PrayerSettings) },
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
                                text = "Calculation Method",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${uiState.calculationMethod.displayName} • ${uiState.juristicMethod.displayName}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Edit calculation settings",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

    // Azan Sound Selection Dialog
    if (showSoundDialog) {
        val sounds = listOf(
            "Makkah Azan",
            "Madinah Azan",
            "Al-Aqsa Azan",
            "Gentle Chime",
            "Beep"
        )
        AlertDialog(
            onDismissRequest = { showSoundDialog = false },
            title = { Text("Select Azan Notification Sound") },
            text = {
                Column {
                    sounds.forEach { sound ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setAzanSound(sound)
                                    showSoundDialog = false
                                    AzanPlayer.playSoundPreview(context, sound)
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uiState.azanSound == sound,
                                onClick = {
                                    viewModel.setAzanSound(sound)
                                    showSoundDialog = false
                                    AzanPlayer.playSoundPreview(context, sound)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = sound,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (uiState.azanSound == sound) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSoundDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
