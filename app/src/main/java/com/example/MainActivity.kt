package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.components.AppBottomNavBar
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.MuslimNamazViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MuslimNamazViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsState()

            val darkTheme = when (uiState.themeMode) {
                "LIGHT" -> false
                "DARK" -> true
                else -> isSystemInDarkTheme()
            }

            MyApplicationTheme(darkTheme = darkTheme) {
                MainAppContent(
                    viewModel = viewModel,
                    destination = uiState.currentDestination,
                    onNavigate = { dest -> viewModel.navigateTo(dest) }
                )
            }
        }
    }
}

@Composable
fun MainAppContent(
    viewModel: MuslimNamazViewModel,
    destination: AppDestination,
    onNavigate: (AppDestination) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Handle Android system back button
    BackHandler(enabled = destination !is AppDestination.Home) {
        when (destination) {
            is AppDestination.QuranReader -> onNavigate(AppDestination.QuranList)
            is AppDestination.QuranList,
            is AppDestination.Hadith,
            is AppDestination.Calendar,
            is AppDestination.Zakat,
            is AppDestination.LocationPicker,
            is AppDestination.PrayerSettings -> onNavigate(AppDestination.Home)
            else -> onNavigate(AppDestination.Home)
        }
    }

    val showBottomBar = destination in listOf(
        AppDestination.Home,
        AppDestination.Namaz,
        AppDestination.Qibla,
        AppDestination.Tasbeeh,
        AppDestination.Duas,
        AppDestination.More
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavBar(
                    currentDestination = destination,
                    onNavigate = onNavigate
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (destination) {
                is AppDestination.Home -> HomeScreen(
                    uiState = uiState,
                    onNavigate = onNavigate,
                    onToggleDuaFavorite = { viewModel.toggleDuaFavorite(it) }
                )
                is AppDestination.Namaz -> NamazScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
                is AppDestination.Qibla -> QiblaScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
                is AppDestination.Tasbeeh -> TasbeehScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                is AppDestination.Duas -> DuasScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                is AppDestination.More -> MoreScreen(
                    uiState = uiState,
                    onNavigate = onNavigate
                )
                is AppDestination.QuranList -> QuranScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
                is AppDestination.QuranReader -> QuranReaderScreen(
                    surahNumber = destination.surahNumber,
                    uiState = uiState,
                    viewModel = viewModel,
                    onBack = { onNavigate(AppDestination.QuranList) }
                )
                is AppDestination.Hadith -> HadithScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
                is AppDestination.Calendar -> IslamicCalendarScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
                is AppDestination.Zakat -> ZakatScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
                is AppDestination.LocationPicker -> LocationPickerScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
                is AppDestination.PrayerSettings -> PrayerSettingsScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
                else -> HomeScreen(
                    uiState = uiState,
                    onNavigate = onNavigate,
                    onToggleDuaFavorite = { viewModel.toggleDuaFavorite(it) }
                )
            }
        }
    }
}
