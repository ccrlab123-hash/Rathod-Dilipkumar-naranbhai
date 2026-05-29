package com.example

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

// Represents a single stroke point
data class DragPoint(val offset: Offset, val color: Color)

@Composable
fun TracingCanvas(
    charToTrace: String,
    modifier: Modifier = Modifier,
    onSuccess: () -> Unit
) {
    val points = remember { mutableStateListOf<DragPoint>() }
    var currentColor by remember { mutableStateOf(Color(0xFFE91E63)) } // Default Bubblegum Pink
    
    val colors = listOf(
        Color(0xFFE91E63), // Pink
        Color(0xFF2196F3), // Blue
        Color(0xFF4CAF50), // Green
        Color(0xFFFF9800), // Orange
        Color(0xFF9C27B0), // Purple
        Color(0xFFFFEB3B)  // Yellow
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFFFFDE7)) // Soft yellowish drawing board
            .border(4.dp, Color(0xFFFFB74D), RoundedCornerShape(24.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Trace the Letter!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Drawing Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            points.add(DragPoint(offset, currentColor))
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val currentPoint = change.position
                            points.add(DragPoint(currentPoint, currentColor))
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            // Dotted Guide in background
            Text(
                text = charToTrace,
                fontSize = 180.sp,
                fontWeight = FontWeight.Black,
                color = Color(0x22000000) // Dotted representation
            )
            
            // Interactive Tracing Layer
            Canvas(modifier = Modifier.fillMaxSize()) {
                if (points.isNotEmpty()) {
                    var lastPoint = points.first()
                    points.forEach { point ->
                        // Draw continuous line segments matching touch dragging
                        drawLine(
                            color = point.color,
                            start = lastPoint.offset,
                            end = point.offset,
                            strokeWidth = 12.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        lastPoint = point
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Color palette & settings row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Palette picking circles
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Palette, contentDescription = "Choose Color", tint = Color(0xFFE65100))
                colors.forEach { col ->
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(col)
                            .border(
                                3.dp,
                                if (currentColor == col) Color(0xFFE65100) else Color.Transparent,
                                CircleShape
                            )
                            .clickable { currentColor = col }
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Control Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Clear button
                FilledTonalButton(
                    onClick = { points.clear() },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0xFFFFEBEE),
                        contentColor = Color(0xFFC62828)
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Clear", fontSize = 12.sp)
                }

                // Success check - triggering audio praise / animation
                Button(
                    onClick = { onSuccess() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("Done! ⭐", fontSize = 12.sp)
                }
            }
        }
    }
}


// Particle for Confetti implementation
data class Particle(
    val id: Int,
    val x: Float,
    val y: Float,
    val color: Color,
    val speedX: Float,
    val speedY: Float,
    val size: Float,
    val rotation: Float,
    val rotationSpeed: Float
)

@Composable
fun ConfettiExplosion(
    triggered: Boolean,
    onFinished: () -> Unit
) {
    if (!triggered) return

    val particles = remember { mutableStateListOf<Particle>() }
    val scope = rememberCoroutineScope()

    // Initialize particles on trigger
    LaunchedEffect(triggered) {
        particles.clear()
        val colors = listOf(
            Color(0xFFFFEB3B), Color(0xFFFF5722), Color(0xFF4CAF50),
            Color(0xFF2196F3), Color(0xFFE91E63), Color(0xFF9C27B0)
        )
        
        // Spawn 35 diverse particles
        for (i in 0..35) {
            particles.add(
                Particle(
                    id = i,
                    x = 0.5f, // percentage based coordinates
                    y = 0.5f,
                    color = colors[Random.nextInt(colors.size)],
                    speedX = (Random.nextFloat() - 0.5f) * 0.12f,
                    speedY = (Random.nextFloat() - 0.8f) * 0.15f,
                    size = Random.nextInt(15, 35).toFloat(),
                    rotation = Random.nextFloat() * 360f,
                    rotationSpeed = (Random.nextFloat() - 0.5f) * 15f
                )
            )
        }

        // Animation update loop representing physical falls
        var elapsed = 0
        while (elapsed < 1600 && particles.isNotEmpty()) {
            delay(16)
            elapsed += 16
            
            for (j in particles.indices) {
                val p = particles[j]
                particles[j] = p.copy(
                    x = p.x + p.speedX,
                    y = p.y + p.speedY + 0.005f, // Gravity effect
                    rotation = p.rotation + p.rotationSpeed
                )
            }
        }
        
        particles.clear()
        onFinished()
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()

        if (particles.isNotEmpty()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                particles.forEach { p ->
                    val posScaleY = p.y * heightPx
                    val posScaleX = p.x * widthPx
                    
                    if (posScaleX in 0f..widthPx && posScaleY in 0f..heightPx) {
                        drawRect(
                            color = p.color,
                            topLeft = Offset(posScaleX, posScaleY),
                            size = androidx.compose.ui.geometry.Size(p.size, p.size),
                            alpha = 0.9f
                        )
                    }
                }
            }
        }
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NurseryRhymeWebView(
    youtubeVideoId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    // Auto construction of clean responsive embedded player
    val htmlData = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
            <style>
                body {
                    margin: 0;
                    padding: 0;
                    background-color: #000000;
                    overflow: hidden;
                }
                .video-container {
                    position: relative;
                    padding-bottom: 56.25%; /* 16:9 Aspect Ratio */
                    height: 0;
                    overflow: hidden;
                    border: 4px solid #FFCC00;
                    border-radius: 16px;
                }
                .video-container iframe {
                    position: absolute;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    border: 0;
                }
            </style>
        </head>
        <body>
            <div class="video-container">
                <iframe 
                    src="https://www.youtube-nocookie.com/embed/$youtubeVideoId?autoplay=1&modestbranding=1&rel=0&showinfo=0&iv_load_policy=3" 
                    allow="autoplay; encrypted-media; picture-in-picture" 
                    allowfullscreen>
                </iframe>
            </div>
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                
                settings.apply {
                    javaScriptEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    domStorageEnabled = true
                    mediaPlaybackRequiresUserGesture = false
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
                
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
            }
        },
        update = { webView ->
            webView.loadDataWithBaseURL("https://www.youtube.com", htmlData, "text/html", "UTF-8", null)
        },
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(4.dp, Color(0xFFFFCA28), RoundedCornerShape(16.dp))
    )
}
