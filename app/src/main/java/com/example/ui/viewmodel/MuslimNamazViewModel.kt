package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.prayer.PrayerCalculator
import com.example.data.preferences.UserPreferences
import com.example.data.repository.IslamicRepository
import com.example.service.AzanPlayer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

sealed class AppDestination {
    object Home : AppDestination()
    object Namaz : AppDestination()
    object Qibla : AppDestination()
    object Tasbeeh : AppDestination()
    object Duas : AppDestination()
    object More : AppDestination()
    // Sub-screens
    data class QuranReader(val surahNumber: Int) : AppDestination()
    object QuranList : AppDestination()
    object Hadith : AppDestination()
    object Calendar : AppDestination()
    object Zakat : AppDestination()
    object Favorites : AppDestination()
    object PrayerSettings : AppDestination()
    object NotificationSettings : AppDestination()
    object LocationPicker : AppDestination()
    object About : AppDestination()
}

data class ZakatState(
    val cash: Double = 0.0,
    val goldGrams: Double = 0.0,
    val goldPricePerGram: Double = 85.0, // Default USD approx
    val silverGrams: Double = 0.0,
    val silverPricePerGram: Double = 1.0,
    val businessAssets: Double = 0.0,
    val debtsLiabilities: Double = 0.0,
    val nisabType: String = "GOLD", // "GOLD" or "SILVER"
    val currency: String = "USD"
) {
    val goldNisabThreshold: Double get() = 87.48 * goldPricePerGram
    val silverNisabThreshold: Double get() = 612.36 * silverPricePerGram
    val activeNisab: Double get() = if (nisabType == "GOLD") goldNisabThreshold else silverNisabThreshold

    val grossWealth: Double
        get() = cash + (goldGrams * goldPricePerGram) + (silverGrams * silverPricePerGram) + businessAssets

    val netZakatableWealth: Double
        get() = kotlin.math.max(0.0, grossWealth - debtsLiabilities)

    val isZakatPayable: Boolean
        get() = netZakatableWealth >= activeNisab

    val zakatAmount: Double
        get() = if (isZakatPayable) netZakatableWealth * 0.025 else 0.0
}

data class UIState(
    val currentDestination: AppDestination = AppDestination.Home,
    val selectedCity: CityLocation = IslamicRepository.CITIES.first(),
    val currentTimeStr: String = "",
    val currentDateGregorianStr: String = "",
    val hijriDate: HijriDate = PrayerCalculator.getHijriDate(),
    val todaySchedule: PrayerSchedule? = null,
    val nextPrayerInfo: NextPrayerInfo? = null,
    val calculationMethod: CalculationMethod = CalculationMethod.MWL,
    val juristicMethod: JuristicMethod = JuristicMethod.STANDARD,
    val completedPrayersToday: Set<String> = emptySet(),
    // Tasbeeh
    val currentTasbeehCount: Int = 0,
    val currentTasbeehTarget: Int = 33,
    val selectedDhikr: TasbeehPreset = IslamicRepository.TASBEEH_PRESETS.first(),
    val tasbeehDailyCount: Int = 0,
    val tasbeehTotalCount: Long = 0L,
    // Duas
    val selectedDuaCategory: String = "All",
    val duaSearchQuery: String = "",
    val favoriteDuaIds: Set<String> = emptySet(),
    // Quran
    val quranSearchQuery: String = "",
    val lastReadSurah: Int = 1,
    val lastReadAyah: Int = 1,
    val bookmarkedSurahs: Set<Int> = emptySet(),
    val quranFontSize: Float = 22f,
    // Hadith
    val hadithSearchQuery: String = "",
    val selectedHadithCollection: String = "All",
    val favoriteHadithIds: Set<String> = emptySet(),
    // Qibla
    val qiblaBearing: Double = 0.0,
    val distanceToKaabaKm: Double = 0.0,
    val deviceAzimuth: Float = 0f,
    // Settings & preferences
    val azanSound: String = "Makkah Azan",
    val isVibrationEnabled: Boolean = true,
    val themeMode: String = "SYSTEM",
    val appLanguage: String = "en",
    val zakatState: ZakatState = ZakatState()
)

class MuslimNamazViewModel(application: Application) : AndroidViewModel(application) {

    val prefs = UserPreferences(application)

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private var clockJob: Job? = null

    init {
        loadPreferences()
        startClockAndCalculations()
    }

    private fun loadPreferences() {
        val savedCity = IslamicRepository.CITIES.find { it.name == prefs.cityName }
            ?: CityLocation(
                prefs.cityName,
                prefs.countryName,
                prefs.latitude,
                prefs.longitude,
                prefs.timeZoneId
            )

        val selectedDhikr = IslamicRepository.TASBEEH_PRESETS.find { it.id == prefs.tasbeehDhikrId }
            ?: IslamicRepository.TASBEEH_PRESETS.first()

        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        val completedSet = PrayerName.values().filter { prefs.isPrayerCompleted(todayStr, it) }
            .map { it.name }.toSet()

        val bearing = PrayerCalculator.calculateQiblaBearing(savedCity.latitude, savedCity.longitude)
        val dist = PrayerCalculator.calculateDistanceToKaabaKm(savedCity.latitude, savedCity.longitude)

        _uiState.value = _uiState.value.copy(
            selectedCity = savedCity,
            calculationMethod = prefs.calculationMethod,
            juristicMethod = prefs.juristicMethod,
            completedPrayersToday = completedSet,
            currentTasbeehCount = prefs.tasbeehCount,
            currentTasbeehTarget = prefs.tasbeehTarget,
            selectedDhikr = selectedDhikr,
            tasbeehDailyCount = prefs.tasbeehDailyCount,
            tasbeehTotalCount = prefs.tasbeehTotalCount,
            favoriteDuaIds = prefs.getFavoriteDuaIds(),
            lastReadSurah = prefs.lastReadSurahNumber,
            lastReadAyah = prefs.lastReadAyahNumber,
            bookmarkedSurahs = prefs.getBookmarkedSurahs(),
            quranFontSize = prefs.quranFontSize,
            favoriteHadithIds = prefs.getFavoriteHadithIds(),
            azanSound = prefs.azanSound,
            isVibrationEnabled = prefs.vibrationEnabled,
            themeMode = prefs.themeMode,
            appLanguage = prefs.appLanguage,
            qiblaBearing = bearing,
            distanceToKaabaKm = dist
        )

        recomputePrayerSchedule()
    }

    private fun startClockAndCalculations() {
        clockJob?.cancel()
        clockJob = viewModelScope.launch {
            while (isActive) {
                val now = Date()
                val timeFormat = SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH)
                val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH)
                val tz = TimeZone.getTimeZone(_uiState.value.selectedCity.timeZone)
                timeFormat.timeZone = tz
                dateFormat.timeZone = tz

                val schedule = _uiState.value.todaySchedule
                val nextPrayer = if (schedule != null) {
                    PrayerCalculator.getNextPrayer(schedule, now.time)
                } else null

                val hijri = PrayerCalculator.getHijriDate(now, prefs.hijriAdjustmentDays)

                _uiState.value = _uiState.value.copy(
                    currentTimeStr = timeFormat.format(now),
                    currentDateGregorianStr = dateFormat.format(now),
                    hijriDate = hijri,
                    nextPrayerInfo = nextPrayer
                )

                delay(1000)
            }
        }
    }

    fun recomputePrayerSchedule() {
        val city = _uiState.value.selectedCity
        val tz = TimeZone.getTimeZone(city.timeZone)
        val schedule = PrayerCalculator.calculateSchedule(
            date = Date(),
            latitude = city.latitude,
            longitude = city.longitude,
            method = _uiState.value.calculationMethod,
            juristic = _uiState.value.juristicMethod,
            timeZone = tz
        )

        val bearing = PrayerCalculator.calculateQiblaBearing(city.latitude, city.longitude)
        val dist = PrayerCalculator.calculateDistanceToKaabaKm(city.latitude, city.longitude)

        _uiState.value = _uiState.value.copy(
            todaySchedule = schedule,
            qiblaBearing = bearing,
            distanceToKaabaKm = dist
        )
    }

    fun navigateTo(destination: AppDestination) {
        _uiState.value = _uiState.value.copy(currentDestination = destination)
    }

    fun selectCity(city: CityLocation) {
        prefs.setLocation(city)
        _uiState.value = _uiState.value.copy(selectedCity = city)
        recomputePrayerSchedule()
    }

    fun updateCoordinates(latitude: Double, longitude: Double, cityName: String = "My Location") {
        val customCity = CityLocation(cityName, "GPS Location", latitude, longitude, TimeZone.getDefault().id)
        prefs.setLocation(customCity)
        _uiState.value = _uiState.value.copy(selectedCity = customCity)
        recomputePrayerSchedule()
    }

    fun requestGpsLocation(context: Context) {
        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
            if (locationManager != null) {
                val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val isNetEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (isGpsEnabled || isNetEnabled) {
                    val provider = if (isGpsEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER
                    val lastKnown = try {
                        locationManager.getLastKnownLocation(provider)
                    } catch (e: SecurityException) {
                        null
                    }

                    if (lastKnown != null) {
                        updateCoordinates(lastKnown.latitude, lastKnown.longitude, "Current Location")
                    } else {
                        // Request single update
                        try {
                            locationManager.requestSingleUpdate(provider, object : LocationListener {
                                override fun onLocationChanged(loc: Location) {
                                    updateCoordinates(loc.latitude, loc.longitude, "Current Location")
                                }
                                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                                override fun onProviderEnabled(provider: String) {}
                                override fun onProviderDisabled(provider: String) {}
                            }, null)
                        } catch (e: SecurityException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setCalculationMethod(method: CalculationMethod) {
        prefs.calculationMethod = method
        _uiState.value = _uiState.value.copy(calculationMethod = method)
        recomputePrayerSchedule()
    }

    fun setJuristicMethod(method: JuristicMethod) {
        prefs.juristicMethod = method
        _uiState.value = _uiState.value.copy(juristicMethod = method)
        recomputePrayerSchedule()
    }

    fun setHijriAdjustment(adjustment: Int) {
        prefs.hijriAdjustmentDays = adjustment
        _uiState.value = _uiState.value.copy(
            hijriDate = PrayerCalculator.getHijriDate(Date(), adjustment)
        )
    }

    fun togglePrayerCompletion(prayer: PrayerName) {
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        prefs.togglePrayerCompleted(todayStr, prayer)
        val updatedSet = PrayerName.values().filter { prefs.isPrayerCompleted(todayStr, it) }
            .map { it.name }.toSet()
        _uiState.value = _uiState.value.copy(completedPrayersToday = updatedSet)

        AzanPlayer.triggerVibration(getApplication(), 50)
    }

    fun setPrayerNotification(prayer: PrayerName, enabled: Boolean) {
        prefs.setPrayerNotificationEnabled(prayer, enabled)
    }

    fun isPrayerNotificationEnabled(prayer: PrayerName): Boolean {
        return prefs.isPrayerNotificationEnabled(prayer)
    }

    fun setAzanSound(sound: String) {
        prefs.azanSound = sound
        _uiState.value = _uiState.value.copy(azanSound = sound)
    }

    fun setVibrationEnabled(enabled: Boolean) {
        prefs.vibrationEnabled = enabled
        _uiState.value = _uiState.value.copy(isVibrationEnabled = enabled)
    }

    fun setThemeMode(mode: String) {
        prefs.themeMode = mode
        _uiState.value = _uiState.value.copy(themeMode = mode)
    }

    fun setAppLanguage(lang: String) {
        prefs.appLanguage = lang
        _uiState.value = _uiState.value.copy(appLanguage = lang)
    }

    // Compass
    fun updateDeviceAzimuth(azimuth: Float) {
        _uiState.value = _uiState.value.copy(deviceAzimuth = azimuth)
    }

    // Tasbeeh
    fun tapTasbeeh() {
        val newCount = _uiState.value.currentTasbeehCount + 1
        val newDaily = _uiState.value.tasbeehDailyCount + 1
        val newTotal = _uiState.value.tasbeehTotalCount + 1

        prefs.tasbeehCount = newCount
        prefs.tasbeehDailyCount = newDaily
        prefs.tasbeehTotalCount = newTotal

        _uiState.value = _uiState.value.copy(
            currentTasbeehCount = newCount,
            tasbeehDailyCount = newDaily,
            tasbeehTotalCount = newTotal
        )

        if (prefs.tasbeehVibration) {
            if (newCount >= _uiState.value.currentTasbeehTarget) {
                AzanPlayer.triggerCelebrationVibration(getApplication())
            } else {
                AzanPlayer.triggerVibration(getApplication(), 30)
            }
        }
    }

    fun resetTasbeeh() {
        prefs.tasbeehCount = 0
        _uiState.value = _uiState.value.copy(currentTasbeehCount = 0)
        AzanPlayer.triggerVibration(getApplication(), 50)
    }

    fun selectDhikr(preset: TasbeehPreset) {
        prefs.tasbeehDhikrId = preset.id
        prefs.tasbeehTarget = preset.target
        _uiState.value = _uiState.value.copy(
            selectedDhikr = preset,
            currentTasbeehTarget = preset.target
        )
    }

    fun setTasbeehTarget(target: Int) {
        prefs.tasbeehTarget = target
        _uiState.value = _uiState.value.copy(currentTasbeehTarget = target)
    }

    // Duas
    fun setDuaCategory(category: String) {
        _uiState.value = _uiState.value.copy(selectedDuaCategory = category)
    }

    fun setDuaSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(duaSearchQuery = query)
    }

    fun toggleDuaFavorite(duaId: String) {
        prefs.toggleDuaFavorite(duaId)
        _uiState.value = _uiState.value.copy(favoriteDuaIds = prefs.getFavoriteDuaIds())
        AzanPlayer.triggerVibration(getApplication(), 35)
    }

    // Quran
    fun setQuranSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(quranSearchQuery = query)
    }

    fun toggleSurahBookmark(surahNumber: Int) {
        prefs.toggleSurahBookmark(surahNumber)
        _uiState.value = _uiState.value.copy(bookmarkedSurahs = prefs.getBookmarkedSurahs())
        AzanPlayer.triggerVibration(getApplication(), 35)
    }

    fun updateLastRead(surahNumber: Int, ayahNumber: Int = 1) {
        prefs.lastReadSurahNumber = surahNumber
        prefs.lastReadAyahNumber = ayahNumber
        _uiState.value = _uiState.value.copy(lastReadSurah = surahNumber, lastReadAyah = ayahNumber)
    }

    fun setQuranFontSize(size: Float) {
        prefs.quranFontSize = size
        _uiState.value = _uiState.value.copy(quranFontSize = size)
    }

    // Hadith
    fun setHadithSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(hadithSearchQuery = query)
    }

    fun setHadithCollection(collection: String) {
        _uiState.value = _uiState.value.copy(selectedHadithCollection = collection)
    }

    fun toggleHadithFavorite(hadithId: String) {
        prefs.toggleHadithFavorite(hadithId)
        _uiState.value = _uiState.value.copy(favoriteHadithIds = prefs.getFavoriteHadithIds())
        AzanPlayer.triggerVibration(getApplication(), 35)
    }

    // Zakat
    fun updateZakatState(newState: ZakatState) {
        _uiState.value = _uiState.value.copy(zakatState = newState)
    }
}
