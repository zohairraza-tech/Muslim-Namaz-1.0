package com.example.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.CalculationMethod
import com.example.data.model.CityLocation
import com.example.data.model.JuristicMethod
import com.example.data.model.PrayerName
import com.example.data.repository.IslamicRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("muslim_namaz_prefs", Context.MODE_PRIVATE)

    // Location
    var cityName: String
        get() = prefs.getString("city_name", "Makkah") ?: "Makkah"
        set(value) = prefs.edit().putString("city_name", value).apply()

    var countryName: String
        get() = prefs.getString("country_name", "Saudi Arabia") ?: "Saudi Arabia"
        set(value) = prefs.edit().putString("country_name", value).apply()

    var latitude: Double
        get() = java.lang.Double.longBitsToDouble(
            prefs.getLong("latitude_bits", java.lang.Double.doubleToLongBits(21.4225))
        )
        set(value) = prefs.edit().putLong("latitude_bits", java.lang.Double.doubleToLongBits(value)).apply()

    var longitude: Double
        get() = java.lang.Double.longBitsToDouble(
            prefs.getLong("longitude_bits", java.lang.Double.doubleToLongBits(39.8262))
        )
        set(value) = prefs.edit().putLong("longitude_bits", java.lang.Double.doubleToLongBits(value)).apply()

    var timeZoneId: String
        get() = prefs.getString("timezone_id", "Asia/Riyadh") ?: "Asia/Riyadh"
        set(value) = prefs.edit().putString("timezone_id", value).apply()

    fun setLocation(city: CityLocation) {
        cityName = city.name
        countryName = city.country
        latitude = city.latitude
        longitude = city.longitude
        timeZoneId = city.timeZone
    }

    // Calculation Method
    var calculationMethod: CalculationMethod
        get() {
            val name = prefs.getString("calc_method", CalculationMethod.MWL.name)
            return try {
                CalculationMethod.valueOf(name ?: CalculationMethod.MWL.name)
            } catch (e: Exception) {
                CalculationMethod.MWL
            }
        }
        set(value) = prefs.edit().putString("calc_method", value.name).apply()

    // Juristic Method (Asr)
    var juristicMethod: JuristicMethod
        get() {
            val name = prefs.getString("juristic_method", JuristicMethod.STANDARD.name)
            return try {
                JuristicMethod.valueOf(name ?: JuristicMethod.STANDARD.name)
            } catch (e: Exception) {
                JuristicMethod.STANDARD
            }
        }
        set(value) = prefs.edit().putString("juristic_method", value.name).apply()

    // Hijri Adjustment
    var hijriAdjustmentDays: Int
        get() = prefs.getInt("hijri_adjustment", 0)
        set(value) = prefs.edit().putInt("hijri_adjustment", value).apply()

    // Notifications & Azan
    fun isPrayerNotificationEnabled(prayer: PrayerName): Boolean {
        return prefs.getBoolean("notif_${prayer.name}", true)
    }

    fun setPrayerNotificationEnabled(prayer: PrayerName, enabled: Boolean) {
        prefs.edit().putBoolean("notif_${prayer.name}", enabled).apply()
    }

    var vibrationEnabled: Boolean
        get() = prefs.getBoolean("vibration_enabled", true)
        set(value) = prefs.edit().putBoolean("vibration_enabled", value).apply()

    var azanSound: String
        get() = prefs.getString("azan_sound", "Makkah Azan") ?: "Makkah Azan"
        set(value) = prefs.edit().putString("azan_sound", value).apply()

    // Prayer Completion Tracking
    fun isPrayerCompleted(dateStr: String, prayer: PrayerName): Boolean {
        val set = prefs.getStringSet("completed_prayers_$dateStr", emptySet()) ?: emptySet()
        return set.contains(prayer.name)
    }

    fun togglePrayerCompleted(dateStr: String, prayer: PrayerName): Boolean {
        val key = "completed_prayers_$dateStr"
        val set = prefs.getStringSet(key, emptySet())?.toMutableSet() ?: mutableSetOf()
        val newState = if (set.contains(prayer.name)) {
            set.remove(prayer.name)
            false
        } else {
            set.add(prayer.name)
            true
        }
        prefs.edit().putStringSet(key, set).apply()
        return newState
    }

    fun getCompletedPrayersCount(dateStr: String): Int {
        return prefs.getStringSet("completed_prayers_$dateStr", emptySet())?.size ?: 0
    }

    // Tasbeeh Data
    var tasbeehCount: Int
        get() = prefs.getInt("tasbeeh_count", 0)
        set(value) = prefs.edit().putInt("tasbeeh_count", value).apply()

    var tasbeehTarget: Int
        get() = prefs.getInt("tasbeeh_target", 33)
        set(value) = prefs.edit().putInt("tasbeeh_target", value).apply()

    var tasbeehDhikrId: String
        get() = prefs.getString("tasbeeh_dhikr_id", "subhanallah") ?: "subhanallah"
        set(value) = prefs.edit().putString("tasbeeh_dhikr_id", value).apply()

    var tasbeehDailyCount: Int
        get() {
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
            val savedDate = prefs.getString("tasbeeh_daily_date", "")
            return if (savedDate == todayStr) prefs.getInt("tasbeeh_daily_count", 0) else 0
        }
        set(value) {
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
            prefs.edit()
                .putString("tasbeeh_daily_date", todayStr)
                .putInt("tasbeeh_daily_count", value)
                .apply()
        }

    var tasbeehTotalCount: Long
        get() = prefs.getLong("tasbeeh_total_count", 0L)
        set(value) = prefs.edit().putLong("tasbeeh_total_count", value).apply()

    var tasbeehVibration: Boolean
        get() = prefs.getBoolean("tasbeeh_vibration", true)
        set(value) = prefs.edit().putBoolean("tasbeeh_vibration", value).apply()

    var tasbeehSound: Boolean
        get() = prefs.getBoolean("tasbeeh_sound", true)
        set(value) = prefs.edit().putBoolean("tasbeeh_sound", value).apply()

    // Favorites: Duas
    fun isDuaFavorite(duaId: String): Boolean {
        val set = prefs.getStringSet("favorite_duas", emptySet()) ?: emptySet()
        return set.contains(duaId)
    }

    fun toggleDuaFavorite(duaId: String): Boolean {
        val set = prefs.getStringSet("favorite_duas", emptySet())?.toMutableSet() ?: mutableSetOf()
        val isFav = if (set.contains(duaId)) {
            set.remove(duaId)
            false
        } else {
            set.add(duaId)
            true
        }
        prefs.edit().putStringSet("favorite_duas", set).apply()
        return isFav
    }

    fun getFavoriteDuaIds(): Set<String> {
        return prefs.getStringSet("favorite_duas", emptySet()) ?: emptySet()
    }

    // Favorites: Hadith
    fun isHadithFavorite(hadithId: String): Boolean {
        val set = prefs.getStringSet("favorite_hadiths", emptySet()) ?: emptySet()
        return set.contains(hadithId)
    }

    fun toggleHadithFavorite(hadithId: String): Boolean {
        val set = prefs.getStringSet("favorite_hadiths", emptySet())?.toMutableSet() ?: mutableSetOf()
        val isFav = if (set.contains(hadithId)) {
            set.remove(hadithId)
            false
        } else {
            set.add(hadithId)
            true
        }
        prefs.edit().putStringSet("favorite_hadiths", set).apply()
        return isFav
    }

    fun getFavoriteHadithIds(): Set<String> {
        return prefs.getStringSet("favorite_hadiths", emptySet()) ?: emptySet()
    }

    // Quran Bookmarks & Last Read
    var lastReadSurahNumber: Int
        get() = prefs.getInt("quran_last_read_surah", 1)
        set(value) = prefs.edit().putInt("quran_last_read_surah", value).apply()

    var lastReadAyahNumber: Int
        get() = prefs.getInt("quran_last_read_ayah", 1)
        set(value) = prefs.edit().putInt("quran_last_read_ayah", value).apply()

    var quranFontSize: Float
        get() = prefs.getFloat("quran_font_size", 22f)
        set(value) = prefs.edit().putFloat("quran_font_size", value).apply()

    fun isSurahBookmarked(surahNumber: Int): Boolean {
        val set = prefs.getStringSet("quran_bookmarks", emptySet()) ?: emptySet()
        return set.contains(surahNumber.toString())
    }

    fun toggleSurahBookmark(surahNumber: Int): Boolean {
        val set = prefs.getStringSet("quran_bookmarks", emptySet())?.toMutableSet() ?: mutableSetOf()
        val key = surahNumber.toString()
        val isBookmarked = if (set.contains(key)) {
            set.remove(key)
            false
        } else {
            set.add(key)
            true
        }
        prefs.edit().putStringSet("quran_bookmarks", set).apply()
        return isBookmarked
    }

    fun getBookmarkedSurahs(): Set<Int> {
        val set = prefs.getStringSet("quran_bookmarks", emptySet()) ?: emptySet()
        return set.mapNotNull { it.toIntOrNull() }.toSet()
    }

    // Theme: "SYSTEM", "LIGHT", "DARK"
    var themeMode: String
        get() = prefs.getString("app_theme_mode", "SYSTEM") ?: "SYSTEM"
        set(value) = prefs.edit().putString("app_theme_mode", value).apply()

    // Language: "en", "ar"
    var appLanguage: String
        get() = prefs.getString("app_language", "en") ?: "en"
        set(value) = prefs.edit().putString("app_language", value).apply()
}
