package com.example.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.service.AzanPlayer
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.MuslimNamazViewModel
import com.example.ui.viewmodel.UIState
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun QiblaScreen(
    uiState: UIState,
    viewModel: MuslimNamazViewModel,
    onNavigate: (AppDestination) -> Unit
) {
    val context = LocalContext.current
    var azimuth by remember { mutableFloatStateOf(0f) }
    var sensorAccuracy by remember { mutableIntStateOf(SensorManager.SENSOR_STATUS_ACCURACY_HIGH) }
    var isSensorAvailable by remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        val rotationSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        val gravitySensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magneticSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        var gravity: FloatArray? = null
        var geomagnetic: FloatArray? = null

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return
                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    val rotationMatrix = FloatArray(9)
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    val orientation = FloatArray(3)
                    SensorManager.getOrientation(rotationMatrix, orientation)
                    val azimuthInDegrees = Math.toDegrees(orientation[0].toDouble()).toFloat()
                    val normalized = (azimuthInDegrees + 360f) % 360f
                    azimuth = normalized
                    viewModel.updateDeviceAzimuth(normalized)
                } else {
                    if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) gravity = event.values
                    if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) geomagnetic = event.values

                    if (gravity != null && geomagnetic != null) {
                        val r = FloatArray(9)
                        val i = FloatArray(9)
                        if (SensorManager.getRotationMatrix(r, i, gravity, geomagnetic)) {
                            val orientation = FloatArray(3)
                            SensorManager.getOrientation(r, orientation)
                            val azimuthInDegrees = Math.toDegrees(orientation[0].toDouble()).toFloat()
                            val normalized = (azimuthInDegrees + 360f) % 360f
                            azimuth = normalized
                            viewModel.updateDeviceAzimuth(normalized)
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                sensorAccuracy = accuracy
            }
        }

        if (rotationSensor != null) {
            sensorManager.registerListener(listener, rotationSensor, SensorManager.SENSOR_DELAY_UI)
        } else if (gravitySensor != null && magneticSensor != null) {
            sensorManager.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_UI)
            sensorManager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_UI)
        } else {
            isSensorAvailable = false
        }

        onDispose {
            sensorManager?.unregisterListener(listener)
        }
    }

    // Smooth needle animation
    val animatedAzimuth by animateFloatAsState(
        targetValue = azimuth,
        animationSpec = spring(stiffness = 300f),
        label = "azimuthAnim"
    )

    val qiblaBearing = uiState.qiblaBearing.toFloat()
    val relativeAngle = (qiblaBearing - animatedAzimuth + 360f) % 360f
    val isFacingKaaba = relativeAngle in 357f..360f || relativeAngle in 0f..3f

    // Vibrate when user directly faces Kaaba
    LaunchedEffect(isFacingKaaba) {
        if (isFacingKaaba) {
            AzanPlayer.triggerVibration(context, 40)
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Qibla Direction",
                subtitle = "${uiState.selectedCity.name} • ${String.format("%.1f°", qiblaBearing)} Bearing",
                actions = {
                    IconButton(onClick = { onNavigate(AppDestination.LocationPicker) }) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Change Location",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Status Pill
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isFacingKaaba) Emerald600 else MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isFacingKaaba) Icons.Filled.CheckCircle else Icons.Outlined.Navigation,
                        contentDescription = "Status",
                        tint = if (isFacingKaaba) PureWhite else Gold600,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isFacingKaaba) "Directly Facing the Holy Kaaba! 🕋"
                        else "Turn device towards ${String.format("%.0f°", qiblaBearing)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isFacingKaaba) PureWhite else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Compass Dial Canvas
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer rotating compass rose with N, S, E, W
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(-animatedAzimuth)
                ) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = size.minDimension / 2 - 12.dp.toPx()

                    // Outer Circle
                    drawCircle(
                        color = Color(0xFF0F6B52).copy(alpha = 0.15f),
                        radius = radius,
                        center = center
                    )
                    drawCircle(
                        color = Color(0xFFD4AF37),
                        radius = radius,
                        center = center,
                        style = Stroke(width = 3.dp.toPx())
                    )

                    // Tick marks around compass
                    for (i in 0 until 360 step 15) {
                        val angleRad = Math.toRadians(i.toDouble() - 90.0)
                        val isMajor = i % 90 == 0
                        val tickLen = if (isMajor) 16.dp.toPx() else 8.dp.toPx()
                        val startX = (center.x + (radius - tickLen) * cos(angleRad)).toFloat()
                        val startY = (center.y + (radius - tickLen) * sin(angleRad)).toFloat()
                        val endX = (center.x + radius * cos(angleRad)).toFloat()
                        val endY = (center.y + radius * sin(angleRad)).toFloat()

                        drawLine(
                            color = if (isMajor) Color(0xFFD4AF37) else Color.Gray.copy(alpha = 0.4f),
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = if (isMajor) 2.5.dp.toPx() else 1.dp.toPx()
                        )
                    }
                }

                // Cardinal text markers (N, S, E, W) rotating with compass
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(-animatedAzimuth)
                ) {
                    Text(
                        text = "N",
                        color = Color(0xFFE53935),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                    Text(
                        text = "S",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                    Text(
                        text = "E",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                    Text(
                        text = "W",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }

                // Kaaba Pointer Needle (Points directly to Qibla relative to device)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(relativeAngle),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = Offset(size.width / 2, size.height / 2)
                        val needleLen = size.minDimension / 2 - 32.dp.toPx()

                        // Top pointer (Gold/Green for Kaaba)
                        val topPath = Path().apply {
                            moveTo(center.x, center.y - needleLen)
                            lineTo(center.x - 12.dp.toPx(), center.y)
                            lineTo(center.x + 12.dp.toPx(), center.y)
                            close()
                        }
                        drawPath(topPath, color = Color(0xFFD4AF37))

                        // Bottom pointer (Dark)
                        val bottomPath = Path().apply {
                            moveTo(center.x, center.y + needleLen * 0.5f)
                            lineTo(center.x - 8.dp.toPx(), center.y)
                            lineTo(center.x + 8.dp.toPx(), center.y)
                            close()
                        }
                        drawPath(bottomPath, color = Color.Gray.copy(alpha = 0.5f))

                        // Center Pivot
                        drawCircle(
                            color = Color(0xFF0F6B52),
                            radius = 14.dp.toPx(),
                            center = center
                        )
                        drawCircle(
                            color = Color(0xFFD4AF37),
                            radius = 6.dp.toPx(),
                            center = center
                        )
                    }

                    // Kaaba Mini Icon at needle tip
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 10.dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0A4D3C)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🕋",
                            fontSize = 18.sp
                        )
                    }
                }
            }

            // Degree & Distance Info Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Qibla Angle",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${String.format("%.1f", qiblaBearing)}°",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Gold700
                        )
                        Text(
                            text = "From North",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Distance to Makkah",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${String.format("%,.0f", uiState.distanceToKaabaKm)} km",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        val miles = uiState.distanceToKaabaKm * 0.621371
                        Text(
                            text = "${String.format("%,.0f", miles)} miles",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Calibration & Accuracy Instruction Note
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Calibration Info",
                        tint = Gold700,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Calibrate compass: Hold device flat away from metallic objects and gently wave it in a figure-8 motion.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
