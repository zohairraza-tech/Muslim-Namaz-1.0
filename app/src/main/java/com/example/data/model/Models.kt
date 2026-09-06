package com.example.data.model

enum class PrayerName(val englishName: String, val arabicName: String) {
    FAJR("Fajr", "الفجر"),
    SUNRISE("Sunrise", "الشروق"),
    DHUHR("Dhuhr", "الظهر"),
    ASR("Asr", "العصر"),
    MAGHRIB("Maghrib", "المغرب"),
    ISHA("Isha", "العشاء");

    val isObligatoryPrayer: Boolean
        get() = this != SUNRISE
}

data class PrayerSchedule(
    val dateStr: String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val fajrMillis: Long,
    val sunriseMillis: Long,
    val dhuhrMillis: Long,
    val asrMillis: Long,
    val maghribMillis: Long,
    val ishaMillis: Long
) {
    fun getTimeForPrayer(name: PrayerName): String = when (name) {
        PrayerName.FAJR -> fajr
        PrayerName.SUNRISE -> sunrise
        PrayerName.DHUHR -> dhuhr
        PrayerName.ASR -> asr
        PrayerName.MAGHRIB -> maghrib
        PrayerName.ISHA -> isha
    }

    fun getMillisForPrayer(name: PrayerName): Long = when (name) {
        PrayerName.FAJR -> fajrMillis
        PrayerName.SUNRISE -> sunriseMillis
        PrayerName.DHUHR -> dhuhrMillis
        PrayerName.ASR -> asrMillis
        PrayerName.MAGHRIB -> maghribMillis
        PrayerName.ISHA -> ishaMillis
    }
}

data class NextPrayerInfo(
    val prayerName: PrayerName,
    val timeFormatted: String,
    val targetMillis: Long,
    val remainingMillis: Long
)

enum class CalculationMethod(
    val displayName: String,
    val fajrAngle: Double,
    val ishaAngle: Double,
    val isIshaFixedMinutes: Boolean = false,
    val ishaMinutesAfterMaghrib: Int = 90
) {
    MWL("Muslim World League", 18.0, 17.0),
    ISNA("Islamic Society of North America (ISNA)", 15.0, 15.0),
    EGYPT("Egyptian General Authority of Survey", 19.5, 17.5),
    MAKKAH("Umm Al-Qura University, Makkah", 18.5, 0.0, true, 90),
    KARACHI("Univ. of Islamic Sciences, Karachi", 18.0, 18.0),
    DUBAI("Dubai / UAE Awqaf", 18.2, 18.2),
    KUWAIT("Kuwait", 18.0, 17.5),
    QATAR("Qatar", 18.0, 0.0, true, 90)
}

enum class JuristicMethod(val displayName: String, val shadowMultiplier: Double) {
    STANDARD("Standard (Shafi'i, Maliki, Hanbali)", 1.0),
    HANAFI("Hanafi", 2.0)
}

data class CityLocation(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val timeZone: String
)

data class HijriDate(
    val day: Int,
    val monthName: String,
    val monthArabicName: String,
    val year: Int,
    val monthIndex: Int
) {
    val formatted: String
        get() = "$day $monthName $year AH"
    val formattedArabic: String
        get() = "$day $monthArabicName $year هـ"
}

data class Dua(
    val id: String,
    val category: String,
    val title: String,
    val arabic: String,
    val transliteration: String,
    val english: String,
    val reference: String,
    val occasion: String = ""
)

data class HadithItem(
    val id: String,
    val collection: String,
    val hadithNumber: String,
    val chapter: String,
    val narrator: String,
    val arabic: String,
    val english: String,
    val grade: String = "Sahih"
)

data class SurahInfo(
    val number: Int,
    val arabicName: String,
    val englishName: String,
    val englishTranslation: String,
    val versesCount: Int,
    val revelationType: String,
    val juzNumber: Int
)

data class AyahItem(
    val surahNumber: Int,
    val ayahNumber: Int,
    val arabicText: String,
    val transliteration: String,
    val translation: String
)

data class JuzInfo(
    val number: Int,
    val arabicName: String,
    val englishName: String,
    val startSurah: String,
    val startAyah: Int
)

data class IslamicEvent(
    val name: String,
    val arabicName: String,
    val hijriDate: String,
    val description: String,
    val isUpcomingInRamadan: Boolean = false
)

data class TasbeehPreset(
    val id: String,
    val title: String,
    val arabic: String,
    val transliteration: String,
    val meaning: String,
    val target: Int = 33
)
