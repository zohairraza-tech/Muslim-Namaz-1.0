package com.example.data.prayer

import com.example.data.model.CalculationMethod
import com.example.data.model.HijriDate
import com.example.data.model.JuristicMethod
import com.example.data.model.NextPrayerInfo
import com.example.data.model.PrayerName
import com.example.data.model.PrayerSchedule
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.*

object PrayerCalculator {

    const val KAABA_LATITUDE = 21.422487
    const val KAABA_LONGITUDE = 39.826206

    private val HIJRI_MONTHS_EN = listOf(
        "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani",
        "Jumada al-Ula", "Jumada al-Akhirah", "Rajab", "Sha'ban",
        "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
    )

    private val HIJRI_MONTHS_AR = listOf(
        "محرم", "صفر", "ربيع الأول", "ربيع الثاني",
        "جمادى الأولى", "جمادى الآخرة", "رجب", "شعبان",
        "رمضان", "شوال", "ذو القعدة", "ذو الحجة"
    )

    fun calculateSchedule(
        date: Date = Date(),
        latitude: Double,
        longitude: Double,
        method: CalculationMethod = CalculationMethod.MWL,
        juristic: JuristicMethod = JuristicMethod.STANDARD,
        timeZone: TimeZone = TimeZone.getDefault()
    ): PrayerSchedule {
        val cal = Calendar.getInstance(timeZone).apply { time = date }
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val tzOffsetHours = timeZone.getOffset(date.time) / 3600000.0

        val jdn = computeJulianDay(year, month, day)
        val d = jdn - 2451545.0

        // Sun's astronomical position
        val g = fixAngle(357.529 + 0.98560028 * d)
        val q = fixAngle(280.459 + 0.98564736 * d)
        val l = fixAngle(q + 1.915 * sin(Math.toRadians(g)) + 0.020 * sin(Math.toRadians(2 * g)))
        val e = 23.439 - 0.00000036 * d

        val raRad = atan2(cos(Math.toRadians(e)) * sin(Math.toRadians(l)), cos(Math.toRadians(l)))
        val raHours = fixAngle(Math.toDegrees(raRad)) / 15.0

        val decRad = asin(sin(Math.toRadians(e)) * sin(Math.toRadians(l)))
        val decDeg = Math.toDegrees(decRad)

        val eqtHours = q / 15.0 - raHours

        // Dhuhr is noon
        val noon = 12.0 + tzOffsetHours - (longitude / 15.0) - eqtHours

        // Fajr
        val fajrHA = hourAngle(latitude, decDeg, -method.fajrAngle)
        val fajrHour = noon - (fajrHA / 15.0)

        // Sunrise & Sunset (approx 0.8333 deg for refraction & sun disc)
        val sunHA = hourAngle(latitude, decDeg, -0.8333)
        val sunriseHour = noon - (sunHA / 15.0)
        val sunsetHour = noon + (sunHA / 15.0)

        // Asr
        val asrAlt = Math.toDegrees(
            atan(1.0 / (juristic.shadowMultiplier + tan(Math.toRadians(abs(latitude - decDeg)))))
        )
        val asrHA = hourAngle(latitude, decDeg, asrAlt)
        val asrHour = noon + (asrHA / 15.0)

        // Maghrib is sunset (+ 2-3 mins safety)
        val maghribHour = sunsetHour + (2.0 / 60.0)

        // Isha
        val ishaHour = if (method.isIshaFixedMinutes) {
            maghribHour + (method.ishaMinutesAfterMaghrib / 60.0)
        } else {
            val ishaHA = hourAngle(latitude, decDeg, -method.ishaAngle)
            noon + (ishaHA / 15.0)
        }

        val fajrMillis = hourToMillis(cal, fajrHour, timeZone)
        val sunriseMillis = hourToMillis(cal, sunriseHour, timeZone)
        val dhuhrMillis = hourToMillis(cal, noon, timeZone)
        val asrMillis = hourToMillis(cal, asrHour, timeZone)
        val maghribMillis = hourToMillis(cal, maghribHour, timeZone)
        val ishaMillis = hourToMillis(cal, ishaHour, timeZone)

        val dateFmt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).apply { this.timeZone = timeZone }

        return PrayerSchedule(
            dateStr = dateFmt.format(date),
            fajr = formatTime(fajrHour),
            sunrise = formatTime(sunriseHour),
            dhuhr = formatTime(noon),
            asr = formatTime(asrHour),
            maghrib = formatTime(maghribHour),
            isha = formatTime(ishaHour),
            fajrMillis = fajrMillis,
            sunriseMillis = sunriseMillis,
            dhuhrMillis = dhuhrMillis,
            asrMillis = asrMillis,
            maghribMillis = maghribMillis,
            ishaMillis = ishaMillis
        )
    }

    fun getNextPrayer(schedule: PrayerSchedule, currentMillis: Long = System.currentTimeMillis()): NextPrayerInfo {
        val pairs = listOf(
            PrayerName.FAJR to schedule.fajrMillis,
            PrayerName.SUNRISE to schedule.sunriseMillis,
            PrayerName.DHUHR to schedule.dhuhrMillis,
            PrayerName.ASR to schedule.asrMillis,
            PrayerName.MAGHRIB to schedule.maghribMillis,
            PrayerName.ISHA to schedule.ishaMillis
        )

        for ((name, timeMillis) in pairs) {
            if (timeMillis > currentMillis) {
                return NextPrayerInfo(
                    prayerName = name,
                    timeFormatted = schedule.getTimeForPrayer(name),
                    targetMillis = timeMillis,
                    remainingMillis = timeMillis - currentMillis
                )
            }
        }

        // All prayers passed for today, next is tomorrow's Fajr
        val tomorrowFajrMillis = schedule.fajrMillis + (24 * 60 * 60 * 1000L)
        return NextPrayerInfo(
            prayerName = PrayerName.FAJR,
            timeFormatted = schedule.fajr,
            targetMillis = tomorrowFajrMillis,
            remainingMillis = max(0L, tomorrowFajrMillis - currentMillis)
        )
    }

    fun calculateQiblaBearing(userLat: Double, userLon: Double): Double {
        val phi1 = Math.toRadians(userLat)
        val phi2 = Math.toRadians(KAABA_LATITUDE)
        val deltaLambda = Math.toRadians(KAABA_LONGITUDE - userLon)

        val y = sin(deltaLambda) * cos(phi2)
        val x = cos(phi1) * sin(phi2) - sin(phi1) * cos(phi2) * cos(deltaLambda)
        val initialBearing = Math.toDegrees(atan2(y, x))
        return (initialBearing + 360.0) % 360.0
    }

    fun calculateDistanceToKaabaKm(userLat: Double, userLon: Double): Double {
        val r = 6371.0 // Earth radius in km
        val dLat = Math.toRadians(KAABA_LATITUDE - userLat)
        val dLon = Math.toRadians(KAABA_LONGITUDE - userLon)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(userLat)) * cos(Math.toRadians(KAABA_LATITUDE)) *
                sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    fun getHijriDate(date: Date = Date(), adjustmentDays: Int = 0): HijriDate {
        val cal = Calendar.getInstance().apply {
            time = date
            add(Calendar.DAY_OF_MONTH, adjustmentDays)
        }
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH) + 1
        val d = cal.get(Calendar.DAY_OF_MONTH)

        val jd = computeJulianDay(y, m, d)
        val l = (jd - 1948440 + 10632).toLong()
        val n = ((l - 1) / 10631).toInt()
        val remainingL = l - 10631 * n + 354
        val j = (((10985 - remainingL) / 5316) * ((50 * remainingL) / 17719) +
                (remainingL / 5670) * ((43 * remainingL) / 15238)).toInt()
        val remainingL2 = remainingL - (((30 - j) / 15) * ((17719 * j) / 50) +
                (j / 16) * ((15238 * j) / 43)) + 29
        val month = ((24 * remainingL2) / 709).toInt()
        val day = (remainingL2 - ((709 * month) / 24)).toInt()
        val year = (30 * n + j - 30)

        val validMonth = month.coerceIn(1, 12)
        val monthIdx = validMonth - 1

        return HijriDate(
            day = day.coerceIn(1, 30),
            monthName = HIJRI_MONTHS_EN.getOrElse(monthIdx) { "Ramadan" },
            monthArabicName = HIJRI_MONTHS_AR.getOrElse(monthIdx) { "رمضان" },
            year = year,
            monthIndex = validMonth
        )
    }

    private fun computeJulianDay(year: Int, month: Int, day: Int): Double {
        var y = year
        var m = month
        if (m <= 2) {
            y -= 1
            m += 12
        }
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        return floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5
    }

    private fun fixAngle(angle: Double): Double {
        var a = angle - 360.0 * floor(angle / 360.0)
        a = if (a < 0) a + 360.0 else a
        return a
    }

    private fun hourAngle(lat: Double, dec: Double, alt: Double): Double {
        val latRad = Math.toRadians(lat)
        val decRad = Math.toRadians(dec)
        val altRad = Math.toRadians(alt)
        val cosHA = (sin(altRad) - sin(latRad) * sin(decRad)) / (cos(latRad) * cos(decRad))
        val clamped = cosHA.coerceIn(-1.0, 1.0)
        return Math.toDegrees(acos(clamped))
    }

    private fun hourToMillis(cal: Calendar, hourFraction: Double, tz: TimeZone): Long {
        val c = Calendar.getInstance(tz).apply {
            timeInMillis = cal.timeInMillis
            val totalSeconds = (fixHour(hourFraction) * 3600).roundToInt()
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
            set(Calendar.SECOND, seconds)
            set(Calendar.MILLISECOND, 0)
        }
        return c.timeInMillis
    }

    private fun fixHour(hour: Double): Double {
        var h = hour - 24.0 * floor(hour / 24.0)
        h = if (h < 0) h + 24.0 else h
        return h
    }

    private fun formatTime(hourFraction: Double): String {
        val totalSecs = (fixHour(hourFraction) * 3600).roundToInt()
        val h = totalSecs / 3600
        val m = (totalSecs % 3600) / 60
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, h)
            set(Calendar.MINUTE, m)
        }
        val fmt = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        return fmt.format(cal.time)
    }
}
