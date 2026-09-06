package com.example.service

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.media.ToneGenerator
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object AzanPlayer {

    private var currentRingtone: Ringtone? = null
    private var toneJob: Job? = null

    fun playSoundPreview(context: Context, soundName: String, onFinished: (() -> Unit)? = null) {
        stop()
        toneJob = CoroutineScope(Dispatchers.Default).launch {
            try {
                when (soundName) {
                    "Beep" -> {
                        val toneGen = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80)
                        toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_NETWORK_LITE, 600)
                        delay(700)
                        toneGen.release()
                    }
                    "Gentle Chime" -> {
                        val toneGen = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 90)
                        toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 300)
                        delay(400)
                        toneGen.startTone(ToneGenerator.TONE_PROP_BEEP2, 500)
                        delay(600)
                        toneGen.release()
                    }
                    else -> {
                        // Makkah Azan, Madinah Azan, Al-Aqsa Azan:
                        // Play melodious notification tone / default alarm ringtone
                        val notificationUri: Uri =
                            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                        val ringtone = RingtoneManager.getRingtone(context, notificationUri)
                        currentRingtone = ringtone
                        ringtone?.play()
                        delay(3500)
                        ringtone?.stop()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                onFinished?.invoke()
            }
        }
    }

    fun triggerVibration(context: Context, durationMs: Long = 40) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(
                        VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE)
                    )
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(durationMs)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun triggerCelebrationVibration(context: Context) {
        try {
            val pattern = longArrayOf(0, 100, 80, 150, 80, 250)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
                } else {
                    @Suppress("DEPRECATION")
                    context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                }
                vibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                @Suppress("DEPRECATION")
                vibrator?.vibrate(pattern, -1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stop() {
        toneJob?.cancel()
        currentRingtone?.stop()
        currentRingtone = null
    }
}
