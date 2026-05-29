package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme
import kotlin.random.Random

enum class LearnTab(val title: String, val icon: ImageVector, val description: String) {
    ALPHABET("Alphabets", Icons.Default.FontDownload, "English, Hindi & Gujarati"),
    NUMBERS("Numbers", Icons.Default.Numbers, "Learn to Count 1-20"),
    MATHS("Basic Maths", Icons.Default.Calculate, "Fun Sums with Confetti"),
    VIDEOS("Animated Videos", Icons.Default.SmartDisplay, "Rhymes & Cartoons")
}

sealed interface LanguageOption {
    object English : LanguageOption
    object Hindi : LanguageOption
    object Gujarati : LanguageOption
}

data class QuizEquation(
    val num1: Int,
    val num2: Int,
    val isAddition: Boolean,
    val options: List<Int>,
    val emoji: String
)

class MainActivity : ComponentActivity() {
    private var ttsManager: PreschoolTtsManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ttsManager = PreschoolTtsManager(this)

        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF6F8FF)), // Vibrant Palette light gray-blue background
                    contentWindowInsets = WindowInsets.safeDrawing
                ) { innerPadding ->
                    PreschoolAppContent(
                        ttsManager = ttsManager,
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color(0xFFF6F8FF))
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsManager?.shutdown()
        ttsManager = null
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PreschoolAppContent(
    ttsManager: PreschoolTtsManager?,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf(LearnTab.ALPHABET) }
    var actionConfettiTrigger by remember { mutableStateOf(false) }

    // Confetti layer overlay
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8FF))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Top Bar
            PreschoolHeaderBar()

            // Main View Area with high-quality crossfade animation
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                when (activeTab) {
                    LearnTab.ALPHABET -> AlphabetScreen(ttsManager) {
                        actionConfettiTrigger = true
                    }
                    LearnTab.NUMBERS -> NumbersScreen(ttsManager) {
                        actionConfettiTrigger = true
                    }
                    LearnTab.MATHS -> MathsScreen(ttsManager) {
                        actionConfettiTrigger = true
                    }
                    LearnTab.VIDEOS -> VideosScreen()
                }
            }

            // Bottom Navigation Deck for kids
            PreschoolTabsDeck(
                activeTab = activeTab,
                onTabSelected = { tab ->
                    activeTab = tab
                    // Speak tab name to assist little kids who cannot read yet!
                    ttsManager?.speakEnglish(tab.title)
                }
            )
        }

        // Particle confetti animation overlay
        ConfettiExplosion(
            triggered = actionConfettiTrigger,
            onFinished = { actionConfettiTrigger = false }
        )
    }
}

@Composable
fun PreschoolHeaderBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "GOOD MORNING!",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0284C7), // sky-600
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "Little Learner",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E293B) // slate-800
            )
        }
        
        // Cute status badge styled like the crown avatar box in the Vibrant Palette HTML mockup
        Box(
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 14.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(1.5.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("👑", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Day 1",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1E293B)
                )
            }
        }
    }
}

@Composable
fun PreschoolTabsDeck(
    activeTab: LearnTab,
    onTabSelected: (LearnTab) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        tonalElevation = 2.dp,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LearnTab.values().forEach { tab ->
                val isSelected = activeTab == tab
                val animScale by animateFloatAsState(
                    targetValue = if (isSelected) 1.05f else 1.0f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
                )
                
                // Color mapping to keep selections vibrant
                val activeBgColor = when (tab) {
                    LearnTab.ALPHABET -> Color(0xFFE0F2FE) // Soft Sky Blue
                    LearnTab.NUMBERS -> Color(0xFFD1FAE5)  // Soft Emerald
                    LearnTab.MATHS -> Color(0xFFFEF3C7)    // Soft Amber
                    LearnTab.VIDEOS -> Color(0xFFFFE4E6)   // Soft Rose
                }
                
                val activeTintColor = when (tab) {
                    LearnTab.ALPHABET -> Color(0xFF0284C7)
                    LearnTab.NUMBERS -> Color(0xFF059669)
                    LearnTab.MATHS -> Color(0xFFD97706)
                    LearnTab.VIDEOS -> Color(0xFFE11D48)
                }

                Column(
                    modifier = Modifier
                        .scale(animScale)
                        .clickable { onTabSelected(tab) }
                        .padding(vertical = 4.dp)
                        .testTag("tab_${tab.name.lowercase()}"),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Active Pill Capsule Container
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(32.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) activeBgColor else Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            tint = if (isSelected) activeTintColor else Color(0xFF94A3B8), // slate-400
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tab.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                        color = if (isSelected) activeTintColor else Color(0xFF64748B), // slate-500
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

// ---------------- ALPHABET SECTION ----------------

@Composable
fun AlphabetScreen(
    ttsManager: PreschoolTtsManager?,
    onConfetti: () -> Unit
) {
    var selectedLang by remember { mutableStateOf<LanguageOption>(LanguageOption.English) }
    var selectedLetterItem by remember { mutableStateOf<AlphabetItem?>(null) }

    val listItems = when (selectedLang) {
        LanguageOption.English -> PreschoolData.englishAlphabets
        LanguageOption.Hindi -> PreschoolData.hindiAlphabets
        LanguageOption.Gujarati -> PreschoolData.gujaratiAlphabets
    }

    // Dynamic Color Setup based on selection
    val themeColor = when (selectedLang) {
        LanguageOption.English -> Color(0xFF0284C7) // sky-600
        LanguageOption.Hindi -> Color(0xFFE11D48)   // rose-600
        LanguageOption.Gujarati -> Color(0xFF059669) // emerald-600
    }

    val themeBgColor = when (selectedLang) {
        LanguageOption.English -> Color(0xFFE0F2FE) // sky-100
        LanguageOption.Hindi -> Color(0xFFFFE4E6)   // rose-100
        LanguageOption.Gujarati -> Color(0xFFD1FAE5) // emerald-100
    }

    val themeBorderColor = when (selectedLang) {
        LanguageOption.English -> Color(0xFFBAE6FD) // sky-200
        LanguageOption.Hindi -> Color(0xFFFECDD3)   // rose-200
        LanguageOption.Gujarati -> Color(0xFFA7F3D0) // emerald-200
    }

    LaunchedEffect(selectedLang) {
        // Auto announce language category
        when (selectedLang) {
            LanguageOption.English -> ttsManager?.speakEnglish("English Alphabets")
            LanguageOption.Hindi -> ttsManager?.speakHindi("हिन्दी वर्णमाला")
            LanguageOption.Gujarati -> ttsManager?.speakGujarati("ગુજરાતી મૂળાક્ષરો")
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Language Selectors Row with Vibrant Palette custom styling
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LanguageButton(
                text = "🔤 English",
                isSelected = selectedLang == LanguageOption.English,
                activeColor = Color(0xFF2196F3),
                activeBgColor = Color(0xFFE0F2FE),
                activeBorderColor = Color(0xFFBAE6FD),
                activeTextColor = Color(0xFF0369A1),
                modifier = Modifier.weight(1f)
            ) {
                selectedLang = LanguageOption.English
                selectedLetterItem = null
            }

            LanguageButton(
                text = "🕉️ हिन्दी",
                isSelected = selectedLang == LanguageOption.Hindi,
                activeColor = Color(0xFFFF9800),
                activeBgColor = Color(0xFFFFE4E6),
                activeBorderColor = Color(0xFFFECDD3),
                activeTextColor = Color(0xFFBE123C),
                modifier = Modifier.weight(1f)
            ) {
                selectedLang = LanguageOption.Hindi
                selectedLetterItem = null
            }

            LanguageButton(
                text = "🌾 ગુજરાતી",
                isSelected = selectedLang == LanguageOption.Gujarati,
                activeColor = Color(0xFF4CAF50),
                activeBgColor = Color(0xFFD1FAE5),
                activeBorderColor = Color(0xFFA7F3D0),
                activeTextColor = Color(0xFF047857),
                modifier = Modifier.weight(1f)
            ) {
                selectedLang = LanguageOption.Gujarati
                selectedLetterItem = null
            }
        }

        if (selectedLetterItem == null) {
            // Display Alphabet grid list with Vibrant Palette rounded layout
            Text(
                text = "Tap on any letter to speak and trace! 🎒",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = themeColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(listItems) { item ->
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .testTag("letter_${item.char}")
                            .clickable {
                                selectedLetterItem = item
                                // Speak immediately on click using active localization voice
                                when (selectedLang) {
                                    LanguageOption.English -> {
                                        ttsManager?.speakEnglish("${item.char}.   ${item.word}")
                                    }
                                    LanguageOption.Hindi -> {
                                        ttsManager?.speakHindi("${item.char}  से ${item.word}")
                                    }
                                    LanguageOption.Gujarati -> {
                                        ttsManager?.speakGujarati("${item.char}  એટલે ${item.word}")
                                    }
                                }
                            },
                        colors = CardDefaults.cardColors(containerColor = themeBgColor),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(2.dp, themeBorderColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                // White circular background for character representation (mockup-style)
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.char,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Black,
                                        color = themeColor
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.emoji,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Detailed letter display, with pronouncer and interactive handwriting Tracing canvas
            val currentItem = selectedLetterItem!!
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Return Navigation button
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(
                        onClick = { selectedLetterItem = null },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Show All Characters", fontWeight = FontWeight.Bold)
                    }
                }

                // Split screen details & canvas side-by-side or stacked
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Left Detailed Card
                    Card(
                        modifier = Modifier
                            .weight(0.42f)
                            .fillMaxHeight(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(28.dp),
                        border = BorderStroke(2.dp, themeBorderColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(themeBgColor)
                                    .border(2.dp, themeColor.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentItem.char,
                                    fontSize = 44.sp,
                                    fontWeight = FontWeight.Black,
                                    color = themeColor
                                )
                            }

                            Text(
                                text = currentItem.emoji,
                                fontSize = 64.sp
                            )

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = currentItem.word,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1E293B) // slate-800
                                )
                                Text(
                                    text = currentItem.meaning,
                                    fontSize = 12.sp,
                                    color = Color(0xFF64748B), // slate-500
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Repeat Speak Button
                            Button(
                                onClick = {
                                    when (selectedLang) {
                                        LanguageOption.English -> {
                                            ttsManager?.speakEnglish("${currentItem.char} for ${currentItem.word}")
                                        }
                                        LanguageOption.Hindi -> {
                                            ttsManager?.speakHindi("${currentItem.char}  से ${currentItem.word}")
                                        }
                                        LanguageOption.Gujarati -> {
                                            ttsManager?.speakGujarati("${currentItem.char} એટલે ${currentItem.word}")
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                                shape = CircleShape,
                                modifier = Modifier.size(54.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Speak Pronunciation", modifier = Modifier.size(24.dp))
                            }
                        }
                    }

                    // Right interactive Tracing Board
                    Box(modifier = Modifier.weight(0.58f).fillMaxHeight()) {
                        TracingCanvas(
                            charToTrace = currentItem.char,
                            modifier = Modifier.fillMaxSize(),
                            onSuccess = {
                                when (selectedLang) {
                                    LanguageOption.English -> ttsManager?.speakEnglish("Beautiful tracing of ${currentItem.char}!")
                                    LanguageOption.Hindi -> ttsManager?.speakHindi("बहुत अच्छे! सुंदर लिखावट!")
                                    LanguageOption.Gujarati -> ttsManager?.speakGujarati("ખૂબ સરસ અક્ષર લેખન!")
                                }
                                onConfetti()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LanguageButton(
    text: String,
    isSelected: Boolean,
    activeColor: Color,
    modifier: Modifier = Modifier,
    activeBgColor: Color = Color.Transparent,
    activeBorderColor: Color = Color.Transparent,
    activeTextColor: Color = Color.White,
    onClick: () -> Unit
) {
    val bg = if (isSelected) {
        if (activeBgColor == Color.Transparent) activeColor else activeBgColor
    } else {
        Color.White
    }
    
    val txColor = if (isSelected) {
        if (activeTextColor == Color.White && activeBgColor != Color.Transparent) activeBgColor else activeTextColor
    } else {
        Color(0xFF475569) // slate-600
    }

    val finalBorderColor = if (isSelected) {
        if (activeBorderColor == Color.Transparent) Color.Transparent else activeBorderColor
    } else {
        Color(0xFFE2E8F0) // slate-200
    }

    Box(
        modifier = modifier
            .height(46.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .border(
                width = 2.dp,
                color = finalBorderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = txColor
        )
    }
}


// ---------------- NUMBERS SECTION ----------------

@Composable
fun NumbersScreen(
    ttsManager: PreschoolTtsManager?,
    onConfetti: () -> Unit
) {
    var activeLang by remember { mutableStateOf<LanguageOption>(LanguageOption.English) }
    var countingNumberSelected by remember { mutableStateOf<NumberItem?>(null) }
    
    // Counting Game state keys
    var countProgress by remember { mutableStateOf(0) }
    val countedItems = remember { mutableStateListOf<Boolean>() }

    val activeColor = when (activeLang) {
        LanguageOption.English -> Color(0xFF6366F1) // Indigo-500
        LanguageOption.Hindi -> Color(0xFFEC4899)   // Rose-500
        LanguageOption.Gujarati -> Color(0xFF10B981) // Emerald-500
    }

    val activeBgColor = when (activeLang) {
        LanguageOption.English -> Color(0xFFEEF2FF) // Indigo-50
        LanguageOption.Hindi -> Color(0xFFFDF2F8)   // Rose-50
        LanguageOption.Gujarati -> Color(0xFFECFDF5) // Emerald-50
    }

    val activeBorderColor = when (activeLang) {
        LanguageOption.English -> Color(0xFFC7D2FE) // Indigo-200
        LanguageOption.Hindi -> Color(0xFFFBCFE8)   // Rose-200
        LanguageOption.Gujarati -> Color(0xFFA7F3D0) // Emerald-200
    }

    val activeTextColor = when (activeLang) {
        LanguageOption.English -> Color(0xFF4F46E5) // Indigo-600
        LanguageOption.Hindi -> Color(0xFFDB2777)   // Rose-600
        LanguageOption.Gujarati -> Color(0xFF059669) // Emerald-600
    }

    LaunchedEffect(activeLang) {
        when (activeLang) {
            LanguageOption.English -> ttsManager?.speakEnglish("Let's count numbers!")
            LanguageOption.Hindi -> ttsManager?.speakHindi("संख्या गिनना सीखें")
            LanguageOption.Gujarati -> ttsManager?.speakGujarati("ચાલો એકડા ગણીએ")
        }
    }

    // Trigger initialization on number pick
    LaunchedEffect(countingNumberSelected) {
        if (countingNumberSelected != null) {
            val num = countingNumberSelected!!.value
            countProgress = 0
            countedItems.clear()
            for (i in 0 until num) {
                countedItems.add(false)
            }
            // Speak standard intro state
            when (activeLang) {
                LanguageOption.English -> ttsManager?.speakEnglish("Beautiful. Let's count up to ${num}. Tap on each item!")
                LanguageOption.Hindi -> ttsManager?.speakHindi("चलो $num तक चीज़ें गिनें")
                LanguageOption.Gujarati -> ttsManager?.speakGujarati("ચાલો $num વસ્તુઓ ગણીએ")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // sub tabs row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LanguageButton(
                text = "🔢 1 2 3",
                isSelected = activeLang == LanguageOption.English,
                activeColor = Color(0xFF673AB7),
                activeBgColor = Color(0xFFEEF2FF),
                activeBorderColor = Color(0xFFC7D2FE),
                activeTextColor = Color(0xFF4F46E5),
                modifier = Modifier.weight(1f)
            ) {
                activeLang = LanguageOption.English
                countingNumberSelected = null
            }

            LanguageButton(
                text = "🕉️ १ २ ३",
                isSelected = activeLang == LanguageOption.Hindi,
                activeColor = Color(0xFFE91E63),
                activeBgColor = Color(0xFFFDF2F8),
                activeBorderColor = Color(0xFFFBCFE8),
                activeTextColor = Color(0xFFDB2777),
                modifier = Modifier.weight(1f)
            ) {
                activeLang = LanguageOption.Hindi
                countingNumberSelected = null
            }

            LanguageButton(
                text = "🌾 ૧ ૨ ૩",
                isSelected = activeLang == LanguageOption.Gujarati,
                activeColor = Color(0xFF009688),
                activeBgColor = Color(0xFFECFDF5),
                activeBorderColor = Color(0xFFA7F3D0),
                activeTextColor = Color(0xFF059669),
                modifier = Modifier.weight(1f)
            ) {
                activeLang = LanguageOption.Gujarati
                countingNumberSelected = null
            }
        }

        if (countingNumberSelected == null) {
            Text(
                text = "Select a number to play the counting game! 🎈",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = activeTextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(PreschoolData.numbersList) { item ->
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .testTag("num_${item.value}")
                            .clickable {
                                countingNumberSelected = item
                                // Call immediate audio pronounce
                                when (activeLang) {
                                    LanguageOption.English -> ttsManager?.speakEnglish("${item.value}")
                                    LanguageOption.Hindi -> ttsManager?.speakHindi(item.englishChar) // fallback speaking Arabic
                                    LanguageOption.Gujarati -> ttsManager?.speakGujarati(item.englishChar)
                                }
                            },
                        colors = CardDefaults.cardColors(containerColor = activeBgColor),
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(2.dp, activeBorderColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                // White circular background badge
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (activeLang == LanguageOption.English) item.englishChar else item.localChar,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Black,
                                        color = activeColor
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.englishWord,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = activeTextColor
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Interactive visual counting playground
            val currentNum = countingNumberSelected!!
            val requiredCount = currentNum.value
            
            Column(modifier = Modifier.fillMaxSize()) {
                // Back Button banner
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { countingNumberSelected = null },
                        colors = ButtonDefaults.buttonColors(containerColor = activeColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("All Numbers", fontWeight = FontWeight.Bold)
                    }

                    // Progress indicator text
                    Text(
                        text = "Counted: $countProgress / $requiredCount 🍎",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = activeTextColor
                    )
                }

                // Interactive Field of Items!
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(28.dp),
                    border = BorderStroke(2.dp, activeBorderColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tap on every item underneath to count it!",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Grid containing items to be tapped
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(5),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(requiredCount) { index ->
                                val isChecked = countedItems.getOrNull(index) ?: false
                                val localItemScale by animateFloatAsState(
                                    targetValue = if (isChecked) 0.82f else 1.15f,
                                    animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
                                )

                                Box(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .scale(localItemScale)
                                        .clip(CircleShape)
                                        .background(
                                            if (isChecked) Color(0xFFE8F5E9) else activeColor.copy(alpha = 0.08f)
                                        )
                                        .border(
                                            2.dp,
                                            if (isChecked) Color(0xFF4CAF50) else activeColor.copy(alpha = 0.3f),
                                            CircleShape
                                        )
                                        .clickable(enabled = !isChecked) {
                                            countedItems[index] = true
                                            countProgress = countedItems.count { it }
                                            
                                            // Pronounce ordinal sequence instantly
                                            when (activeLang) {
                                                LanguageOption.English -> ttsManager?.speakEnglish("$countProgress")
                                                LanguageOption.Hindi -> ttsManager?.speakHindi("$countProgress")
                                                LanguageOption.Gujarati -> ttsManager?.speakGujarati("$countProgress")
                                            }

                                            // Confetti trigger if finished
                                            if (countProgress == requiredCount) {
                                                when (activeLang) {
                                                    LanguageOption.English -> ttsManager?.speakEnglish("Superb! You counted to $requiredCount!")
                                                    LanguageOption.Hindi -> ttsManager?.speakHindi("क्या बात है! आपने $requiredCount तक गिन लिया!")
                                                    LanguageOption.Gujarati -> ttsManager?.speakGujarati("જબરદસ્ત! તમે $requiredCount ગણી લીધા!")
                                                }
                                                onConfetti()
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = if (isChecked) "⭐️" else "🎈",
                                            fontSize = 28.sp
                                        )
                                        if (isChecked) {
                                            Text(
                                                text = "${index + 1}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = Color(0xFF2E7D32)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Completed state banner
                        if (countProgress == requiredCount) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFE8F5E9))
                                    .padding(12.dp)
                                    .border(1.dp, Color(0xFF81C784), RoundedCornerShape(16.dp)),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🎉", fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Awesome Job! Completed Count of $requiredCount",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF2E7D32)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


// ---------------- MATHS SECTION ----------------

@Composable
fun MathsScreen(
    ttsManager: PreschoolTtsManager?,
    onConfetti: () -> Unit
) {
    var quizModel by remember { mutableStateOf<QuizEquation?>(null) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var evaluationMessage by remember { mutableStateOf("") }
    var isCorrectOption by remember { mutableStateOf<Boolean?>(null) }

    val pinkColor = Color(0xFFE91E63)
    val purpleColor = Color(0xFF9C27B0)

    val generateQuiz = {
        val isAdd = Random.nextBoolean()
        val numEmoji = listOf("🍎", "🎈", "🌟", "🐼", "🦆").random()
        
        if (isAdd) {
            val n1 = Random.nextInt(1, 5)
            val n2 = Random.nextInt(1, 5)
            val correctAns = n1 + n2
            val options = mutableListOf(correctAns, correctAns + 1, (correctAns - 1).coerceAtLeast(1))
            options.shuffle()
            
            quizModel = QuizEquation(
                num1 = n1,
                num2 = n2,
                isAddition = true,
                options = options.distinct().take(3),
                emoji = numEmoji
            )
        } else {
            // Subtraction: larger - smaller
            val n1 = Random.nextInt(2, 8)
            val n2 = Random.nextInt(1, n1)
            val correctAns = n1 - n2
            val options = mutableListOf(correctAns, correctAns + 1, (correctAns - 1).coerceAtLeast(0))
            options.shuffle()
            
            quizModel = QuizEquation(
                num1 = n1,
                num2 = n2,
                isAddition = false,
                options = options.distinct().take(3),
                emoji = numEmoji
            )
        }
        selectedAnswer = null
        evaluationMessage = ""
        isCorrectOption = null
    }

    LaunchedEffect(Unit) {
        generateQuiz()
        ttsManager?.speakEnglish("Welcome to Math Playground. Let's play dynamic addition and subtraction puzzles!")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (quizModel != null) {
            val q = quizModel!!
            
            Text(
                text = if (q.isAddition) "Interactive Addition ➕" else "Interactive Subtraction ➖",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = if (q.isAddition) Color(0xFF059669) else Color(0xFFE11D48), // emerald / rose
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(2.dp, Color(0xFFE2E8F0)), // slate-200
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    // Question banner
                    Text(
                        text = if (q.isAddition) {
                            "How many ${q.emoji} are there together?"
                        } else {
                            "Take away ${q.num2} ${q.emoji} from ${q.num1} ${q.emoji}. How many are left?"
                        },
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF334155), // slate-700
                        textAlign = TextAlign.Center
                    )

                    // VISUAL MATHEMATICAL GRID ROW SHOWING REAL ASSETS WITH MATH SYMBOLS
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Num 1 items display container
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .border(2.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                                .padding(8.dp)
                        ) {
                            repeat(q.num1) {
                                Text(text = q.emoji, fontSize = 26.sp)
                            }
                        }

                        // Math Sign
                        Text(
                            text = if (q.isAddition) "  ➕  " else "  ➖  ",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = if (q.isAddition) Color(0xFF10B981) else Color(0xFFEF4444)
                        )

                        // Num 2 items display container
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    if (q.isAddition) Color(0xFFE2E8F0) else Color(0xFFFECDD3), // slate or rose-200
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(8.dp)
                        ) {
                            repeat(q.num2) {
                                Text(
                                    text = q.emoji,
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .scale(if (q.isAddition) 1.0f else 0.85f)
                                        .alpha(if (q.isAddition) 1.0f else 0.35f) // fade out if subtraction!
                                )
                            }
                        }

                        // Equals sign
                        Text(
                            text = "  ＝  ",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF64748B) // slate-500
                        )

                        // Question block
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFFEF08A)) // amber-100
                                .border(2.dp, Color(0xFFF59E0B), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = selectedAnswer?.toString() ?: "❓",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFD97706) // active amber
                            )
                        }
                    }

                    // Equation representation text underneath
                    Text(
                        text = if (q.isAddition) {
                            "${q.num1}  +  ${q.num2}  =  ${selectedAnswer?.toString() ?: "?"}"
                        } else {
                            "${q.num1}  -  ${q.num2}  =  ${selectedAnswer?.toString() ?: "?"}"
                        },
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1E293B) // slate-800
                    )

                    // ANSWER OPTION SELECTORS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        q.options.forEach { option ->
                            val isBtnSelected = selectedAnswer == option
                            val containerClr = if (isBtnSelected) {
                                if (isCorrectOption == true) Color(0xFF10B981) else Color(0xFFEF4444)
                            } else {
                                Color.White
                            }
                            val contentClr = if (isBtnSelected) Color.White else Color(0xFF1E293B)
                            val borderClr = if (isBtnSelected) {
                                Color.Transparent
                            } else {
                                Color(0xFFE2E8F0)
                            }

                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(containerClr)
                                    .border(2.dp, borderClr, CircleShape)
                                    .clickable(enabled = selectedAnswer == null) {
                                        selectedAnswer = option
                                        val correct = if (q.isAddition) q.num1 + q.num2 else q.num1 - q.num2
                                        if (option == correct) {
                                            isCorrectOption = true
                                            evaluationMessage = "Fantastic! That's correct! ⭐"
                                            val speakText = if (q.isAddition) {
                                                "Awesome job! ${q.num1} plus ${q.num2} equals ${correct}!"
                                            } else {
                                                "Fantastic! ${q.num1} minus ${q.num2} equals ${correct}!"
                                            }
                                            ttsManager?.speakEnglish(speakText)
                                            onConfetti()
                                        } else {
                                            isCorrectOption = false
                                            evaluationMessage = "Let's try again! Count all remaining items!"
                                            ttsManager?.speakEnglish("Let's try again! Count carefully.")
                                        }
                                    }
                                    .testTag("math_option_$option"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$option",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Black,
                                    color = contentClr
                                )
                            }
                        }
                    }

                    // Prompt Response banner
                    if (evaluationMessage.isNotEmpty()) {
                        Text(
                            text = evaluationMessage,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCorrectOption == true) Color(0xFF059669) else Color(0xFFE11D48),
                            modifier = Modifier.animateContentSize()
                        )
                    }

                    // Next button
                    Button(
                        onClick = { generateQuiz() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)), // Indigo accent
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Next Puzzle", fontSize = 16.sp, fontWeight = FontWeight.Black)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                        }
                    }
                }
            }
        }
    }
}


// ---------------- VIDEOS CARTOON SECTION ----------------

@Composable
fun VideosScreen() {
    var selectedVideoCategory by remember { mutableStateOf("English") }
    var activeVideoItem by remember { mutableStateOf<AnimatedVideoItem?>(PreschoolData.animatedVideos.firstOrNull()) }

    val currentList = PreschoolData.animatedVideos.filter { it.language == selectedVideoCategory }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Animated Kids Nursery Rhymes 🦉🍿",
            fontSize = 17.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFFE11D48), // beautiful rose-600
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // Web view card housing active video
        if (activeVideoItem != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(2.dp, Color(0xFFFECDD3)) // rose-200
            ) {
                NurseryRhymeWebView(
                    youtubeVideoId = activeVideoItem!!.youtubeId,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            // Player title details
            Text(
                text = "Playing: ${activeVideoItem!!.title}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF475569), // slate-600
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // Language playlists selector (reusing our styling system!)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("English", "Hindi", "Gujarati").forEach { cat ->
                val isSelected = selectedVideoCategory == cat
                LanguageButton(
                    text = if (cat == "English") "💂 English" else if (cat == "Hindi") "🕉️ हिन्दी" else "🌾 ગુજરાતી",
                    isSelected = isSelected,
                    activeColor = Color(0xFFE11D48),
                    activeBgColor = Color(0xFFFFE4E6),
                    activeBorderColor = Color(0xFFFECDD3),
                    activeTextColor = Color(0xFFBE123C),
                    modifier = Modifier.weight(1f)
                ) {
                    selectedVideoCategory = cat
                    activeVideoItem = PreschoolData.animatedVideos.firstOrNull { it.language == cat }
                }
            }
        }

        // Scrollable playlist items
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(currentList) { video ->
                val isPlayingThis = activeVideoItem?.youtubeId == video.youtubeId
                val containerColor = if (isPlayingThis) Color(0xFFFFE4E6) else Color.White
                val borderColor = if (isPlayingThis) Color(0xFFFECDD3) else Color(0xFFE2E8F0) // slate-200

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { activeVideoItem = video },
                    colors = CardDefaults.cardColors(
                        containerColor = containerColor
                    ),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(2.dp, borderColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Play emoji circular frame
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isPlayingThis) Color(0xFFE11D48) else Color(0xFFF1F5F9) // rose-600 or slate-100
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isPlayingThis) "🎬" else video.emoji,
                                fontSize = 20.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = video.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isPlayingThis) Color(0xFF9F1239) else Color(0xFF334155) // slate-700
                            )
                            Text(
                                text = video.description,
                                fontSize = 11.sp,
                                color = Color(0xFF64748B), // slate-500
                                maxLines = 1
                            )
                        }
                        
                        if (isPlayingThis) {
                            Text(
                                text = "NOW PLAYING 🍿",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFBE123C),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFFECDD3))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        } else {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play Video",
                                tint = Color(0xFF94A3B8), // slate-400
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
