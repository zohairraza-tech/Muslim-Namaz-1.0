package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.CalculationMethod
import com.example.data.model.JuristicMethod
import com.example.data.model.PrayerName
import com.example.data.prayer.PrayerCalculator
import com.example.data.repository.IslamicRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date
import java.util.TimeZone

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Muslim Namaz", appName)
    }

    @Test
    fun `prayer calculator computes all prayers for Makkah`() {
        val schedule = PrayerCalculator.calculateSchedule(
            date = Date(),
            latitude = 21.4225,
            longitude = 39.8262,
            method = CalculationMethod.MAKKAH,
            juristic = JuristicMethod.STANDARD,
            timeZone = TimeZone.getTimeZone("Asia/Riyadh")
        )

        assertNotNull(schedule.fajr)
        assertNotNull(schedule.sunrise)
        assertNotNull(schedule.dhuhr)
        assertNotNull(schedule.asr)
        assertNotNull(schedule.maghrib)
        assertNotNull(schedule.isha)
        assertTrue(schedule.fajr.isNotBlank())
        assertTrue(schedule.dhuhr.isNotBlank())
    }

    @Test
    fun `qibla calculation from Cairo points southeast`() {
        // Cairo coords: 30.0444, 31.2357
        val bearing = PrayerCalculator.calculateQiblaBearing(30.0444, 31.2357)
        // Cairo to Makkah is approximately 136°
        assertTrue("Bearing should be around 136 degrees: was $bearing", bearing in 130.0..142.0)
    }

    @Test
    fun `islamic repository contains curated essential data`() {
        assertTrue(IslamicRepository.CITIES.isNotEmpty())
        assertTrue(IslamicRepository.DUAS.isNotEmpty())
        assertTrue(IslamicRepository.SURAHS.size == 114)
        assertTrue(IslamicRepository.HADITH_LIST.isNotEmpty())
        assertTrue(IslamicRepository.TASBEEH_PRESETS.isNotEmpty())
    }
}
