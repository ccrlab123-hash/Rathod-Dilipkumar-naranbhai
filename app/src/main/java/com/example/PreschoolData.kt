package com.example

data class AlphabetItem(
    val char: String,
    val word: String,
    val phonetics: String,
    val meaning: String,
    val emoji: String
)

data class NumberItem(
    val value: Int,
    val englishChar: String,
    val localChar: String,
    val englishWord: String,
    val translationWord: String
)

data class AnimatedVideoItem(
    val title: String,
    val description: String,
    val youtubeId: String,
    val language: String,
    val emoji: String
)

object PreschoolData {

    val englishAlphabets = listOf(
        AlphabetItem("A", "Apple", "Ay for Apple", "🍎 red juicy fruit", "🍎"),
        AlphabetItem("B", "Ball", "Bee for Ball", "⚽ round bouncy toy", "⚽"),
        AlphabetItem("C", "Cat", "Cee for Cat", "🐱 cute pet animal", "🐱"),
        AlphabetItem("D", "Dog", "Dee for Dog", "🐶 friendly playful pet", "🐶"),
        AlphabetItem("E", "Elephant", "Ee for Elephant", "🐘 large wild mammal", "🐘"),
        AlphabetItem("F", "Fish", "Ef for Fish", "🐟 swimming sea creature", "🐟"),
        AlphabetItem("G", "Grapes", "Gee for Grapes", "🍇 sweet bunch of fruits", "🍇"),
        AlphabetItem("H", "Horse", "Aich for Horse", "🐴 fast beautiful mammal", "🐴"),
        AlphabetItem("I", "Ice-cream", "Eye for Ice-cream", "🍦 cold yummy dessert", "🍦"),
        AlphabetItem("J", "Joker", "Jay for Joker", "🤡 funny circuses performer", "🤡"),
        AlphabetItem("K", "Kite", "Kay for Kite", "🪁 flying wind paper", "🪁"),
        AlphabetItem("L", "Lion", "El for Lion", "🦁 mighty king of jungle", "🦁"),
        AlphabetItem("M", "Monkey", "Em for Monkey", "🐒 active swinging creature", "🐒"),
        AlphabetItem("N", "Nest", "En for Nest", "🪹 cozy bird home", "🪹"),
        AlphabetItem("O", "Orange", "Oh for Orange", "🍊 round sweet citrus", "🍊"),
        AlphabetItem("P", "Parrot", "Pee for Parrot", "🦜 beautiful green talking bird", "🦜"),
        AlphabetItem("Q", "Queen", "Cue for Queen", "👑 royal lady crown", "👑"),
        AlphabetItem("R", "Rabbit", "Arr for Rabbit", "🐇 cute hopping hopper", "🐇"),
        AlphabetItem("S", "Sun", "Es for Sun", "☀️ bright yellow star", "☀️"),
        AlphabetItem("T", "Train", "Tee for Train", "🚂 chugging railway track", "🚂"),
        AlphabetItem("U", "Umbrella", "You for Umbrella", "☂️ protective rain shield", "☂️"),
        AlphabetItem("V", "Van", "Vee for Van", "🚐 family road vehicle", "🚐"),
        AlphabetItem("W", "Watch", "Double-you for Watch", "⌚ handy wrist clock", "⌚"),
        AlphabetItem("X", "Xylophone", "Ex for Xylophone", "🎹 colorful kids instrument", "🎹"),
        AlphabetItem("Y", "Yak", "Wye for Yak", "🐂 furry mountain bull", "🐂"),
        AlphabetItem("Z", "Zebra", "Zed for Zebra", "🦓 striped running horse", "🦓")
    )

    val hindiAlphabets = listOf(
        // Vowels (Swar)
        AlphabetItem("अ", "अनार", "Ah for Anar", "Pomegranate (अनार)", "🍎"),
        AlphabetItem("आ", "आम", "Aa for Aam", "Mango (आम)", "🥭"),
        AlphabetItem("इ", "इमली", "Ih for Imli", "Tamarind (इमली)", "🍇"),
        AlphabetItem("ई", "ईख", "Ee for Eekh", "Sugarcane (ईख)", "🌾"),
        AlphabetItem("उ", "उल्लू", "Uh for Ullu", "Owl (उल्लू)", "🦉"),
        AlphabetItem("ऊ", "ऊन", "Oo for Oon", "Wool (ऊन)", "🧶"),
        AlphabetItem("ए", "एड़ी", "Eh for Ehdi", "Heel (एड़ी)", "🦶"),
        AlphabetItem("ऐ", "ऐनक", "Ai for Ainak", "Glasses (ऐनक)", "👓"),
        AlphabetItem("ओ", "ओखली", "Oh for Okhli", "Mortar (ओखली)", "🥣"),
        AlphabetItem("औ", "औरत", "Au for Aurat", "Woman (औरत)", "👩"),
        AlphabetItem("अं", "अंगूर", "Ang for Angoor", "Grapes (अंगूर)", "🍇"),
        
        // Consonants (Vyanjan)
        AlphabetItem("क", "कबूतर", "Ka for Kabootar", "Pigeon (कबूतर)", "🐦"),
        AlphabetItem("ख", "खरगोश", "Kha for Khargosh", "Rabbit (खरगोश)", "🐇"),
        AlphabetItem("ग", "गमला", "Ga for Gamla", "Flowerpot (गमला)", "🪴"),
        AlphabetItem("घ", "घर", "Gha for Ghar", "House (घर)", "🏠"),
        AlphabetItem("च", "चरखा", "Cha for Charkha", "Spinning Wheel (चरखा)", "🧵"),
        AlphabetItem("छ", "छतरी", "Chha for Chhatri", "Umbrella (छतरी)", "☂️"),
        AlphabetItem("ज", "जहाज", "Ja for Jahaj", "Ship (जहाज)", "🚢"),
        AlphabetItem("झ", "झंडा", "Jha for Jhanda", "Flag (झंडा)", "🚩"),
        AlphabetItem("ट", "टमाटर", "Ta for Tamatar", "Tomato (टमाटर)", "🍅"),
        AlphabetItem("ठ", "ठठेरा", "Tha for Thatheira", "Coppersmith (ठठेरा)", "🔨"),
        AlphabetItem("ड", "डमरू", "Da for Damru", "Drum (डमरू)", "🪘"),
        AlphabetItem("ढ", "ढक्कन", "Dha for Dhakkan", "Lid (ढक्कन)", "⚙️"),
        AlphabetItem("त", "तरबूज", "Ta for Tarbooj", "Watermelon (तरबूज)", "🍉"),
        AlphabetItem("थ", "थर्मस", "Tha for Thermos", "Flask (थर्मस)", "🧴"),
        AlphabetItem("द", "दवात", "Da for Dawaat", "Inkpot (दवात)", "🧪"),
        AlphabetItem("ध", "धनुष", "Dha for Dhanush", "Bow (धनुष)", "🏹"),
        AlphabetItem("न", "नल", "Na for Nal", "Tap (नल)", "🚰"),
        AlphabetItem("प", "पतंग", "Pa for Patang", "Kite (पतंग)", "🪁"),
        AlphabetItem("फ", "फल", "Pha for Phal", "Fruits (फल)", "🍎"),
        AlphabetItem("ब", "बकरी", "Ba for Bakri", "Goat (बकरी)", "🐐"),
        AlphabetItem("भ", "भालू", "Bha for Bhalu", "Bear (भालू)", "🐻"),
        AlphabetItem("म", "मछली", "Ma for Machhli", "Fish (मछली)", "🐟"),
        AlphabetItem("य", "यज्ञ", "Ya for Yagya", "Holy Fire (यज्ञ)", "🔥"),
        AlphabetItem("र", "रथ", "Ra for Rath", "Chariot (रथ)", "🐎"),
        AlphabetItem("ल", "लट्टू", "La for Lattu", "Spinning Top (लट्टू)", "🪀"),
        AlphabetItem("व", "वन", "Va for Van", "Forest (वन)", "🌳"),
        AlphabetItem("श", "शलगम", "Sha for Shalgam", "Turnip (शलगम)", "🧅"),
        AlphabetItem("स", "सपेरा", "Sa for Sapera", "Snake Charmer (सपेरा)", "🐍"),
        AlphabetItem("ह", "हाथी", "Ha for Hathi", "Elephant (हाथी)", "🐘"),
        AlphabetItem("क्ष", "क्षत्रिय", "Ksha for Kshatriya", "Warrior (क्षत्रिय)", "⚔️"),
        AlphabetItem("त्र", "त्रिशूल", "Tra for Trishul", "Trident (त्रिशूल)", "🔱"),
        AlphabetItem("ज्ञ", "ज्ञानी", "Gya for Gyani", "Scholar (ज्ञानी)", "🎓")
    )

    val gujaratiAlphabets = listOf(
        // Vowels (Swar)
        AlphabetItem("અ", "અનાનસ", "Ah for Ananas", "Pineapple (અનાનસ)", "🍍"),
        AlphabetItem("આ", "આમલી", "Aa for Aambli", "Tamarind (આમલી)", "🍇"),
        AlphabetItem("ઇ", "ઇમારત", "Ih for Imarat", "Building (ઇમારત)", "🏢"),
        AlphabetItem("ઈ", "ઈશ્વર", "Ee for Ishwar", "God (ઈશ્વર)", "🛐"),
        AlphabetItem("ઉ", "ઉંદર", "Uh for Undar", "Mouse (ઉંદર)", "🐭"),
        AlphabetItem("ઊ", "ઊંટ", "Oo for Oont", "Camel (ઊંટ)", "🐫"),
        AlphabetItem("એ", "એરણ", "Eh for Eran", "Anvil (એરણ)", "🔨"),
        AlphabetItem("ઐ", "ઐરાવત", "Ai for Airavat", "White Elephant (ઐરાવત)", "🐘"),
        AlphabetItem("ઓ", "ઓશીકું", "Oh for Oshiku", "Pillow (ઓશીકું)", "🛏️"),
        AlphabetItem("ઔ", "ઔષધ", "Au for Aushadh", "Medicine (ઔષધ)", "🧪"),
        AlphabetItem("અં", "અંજીર", "Ang for Anjir", "Fig (અંજીર)", "🫒"),

        // Consonants (Vyanjan)
        AlphabetItem("ક", "કમળ", "Ka for Kamal", "Lotus (કમળ)", "🪷"),
        AlphabetItem("ખ", "ખલ", "Kha for Khal", "Mortar (ખલ)", "🥣"),
        AlphabetItem("ગ", "ગણપતિ", "Ga for Ganpati", "Lord Ganesha (ગણપતિ)", "🐘"),
        AlphabetItem("ઘ", "ઘર", "Gha for Ghar", "House (ઘર)", "🏠"),
        AlphabetItem("ચ", "ચકલી", "Cha for Chakli", "Sparrow (ચકલી)", "🐦"),
        AlphabetItem("છ", "છત્રી", "Chha for Chhatri", "Umbrella (છત્રી)", "☂️"),
        AlphabetItem("જ", "જમરૂખ", "Ja for Jamarukh", "Guava (જમરૂખ)", "🍐"),
        AlphabetItem("ઝ", "ઝભલું", "Jha for Zabhlun", "Frock (ઝભલું)", "👗"),
        AlphabetItem("ટ", "ટમેટું", "Ta for Tametun", "Tomato (ટમેટું)", "🍅"),
        AlphabetItem("ઠ", "ઠળિયો", "Tha for Thaliyo", "Seed/Stone (ઠળિયો)", "🦴"),
        AlphabetItem("ડ", "ડમરું", "Da for Damru", "Drum (ડમરું)", "🪘"),
        AlphabetItem("ઢ", "ઢોલ", "Dha for Dhol", "Drum (ઢોલ)", "🥁"),
        AlphabetItem("ત", "તરબૂચ", "Ta for Tarbuch", "Watermelon (તરબૂચ)", "🍉"),
        AlphabetItem("થ", "થર્મોસ", "Tha for Thermos", "Flask (થર્મોસ)", "🧴"),
        AlphabetItem("દ", "દફતર", "Da for Daftar", "School Bag (દફતર)", "🎒"),
        AlphabetItem("ધ", "ધનુષ", "Dha for Dhanush", "Bow (ધनुષ)", "🏹"),
        AlphabetItem("ન", "નળ", "Na for Nal", "Tap (નળ)", "🚰"),
        AlphabetItem("પ", "પતંગ", "Pa for Patang", "Kite (પતંગ)", "🪁"),
        AlphabetItem("ફ", "ફટાકડા", "Pha for Phataakda", "Firecrackers (જિઝારા)", "🎆"),
        AlphabetItem("બ", "બતક", "Ba for Batak", "Duck (બતક)", "🦆"),
        AlphabetItem("ભ", "ભમરડો", "Bha for Bhamardo", "Spinning Top (ભમરડો)", "🪀"),
        AlphabetItem("મ", "મરચું", "Ma for Marchun", "Chilli (મરચું)", "🌶️"),
        AlphabetItem("ય", "યતિ", "Ya for Yati", "Monk (યતિ)", "🧘"),
        AlphabetItem("ર", "રમકડું", "Ra for Ramakdun", "Toy (રમકડું)", "🧸"),
        AlphabetItem("લ", "લસણ", "La for Lasan", "Garlic (લસણ)", "🧄"),
        AlphabetItem("વ", "વહાણ", "Va for Vahan", "Ship (વહાણ)", "🚢"),
        AlphabetItem("શ", "શરણાઈ", "Sha for Sharnai", "Clarinet (શરણાઈ)", "🎷"),
        AlphabetItem("સ", "સસલું", "Sa for Saslun", "Rabbit (સસલું)", "🐇"),
        AlphabetItem("હ", "હરણ", "Ha for Haran", "Deer (હરણ)", "🦌"),
        AlphabetItem("ળ", "નળ", "La for Nal", "Tap (નળ)", "🚰"),
        AlphabetItem("ક્ષ", "ક્ષત્રિય", "Ksha for Kshatriya", "Warrior (ક્ષત્રિય)", "⚔️"),
        AlphabetItem("જ્ઞ", "જ્ઞાન", "Gya for Gyan", "Knowledge (જ્ઞાન)", "🎓")
    )

    val numbersList = listOf(
        NumberItem(1, "1", "१", "One", "एक (Hindi) / એક (Gujarati)"),
        NumberItem(2, "2", "२", "Two", "दो (Hindi) / બે (Gujarati)"),
        NumberItem(3, "3", "३", "Three", "तीन (Hindi) / ત્રણ (Gujarati)"),
        NumberItem(4, "4", "४", "Four", "चार (Hindi) / ચાર (Gujarati)"),
        NumberItem(5, "5", "५", "Five", "पाँच (Hindi) / પાંચ (Gujarati)"),
        NumberItem(6, "6", "६", "Six", "छह (Hindi) / છ (Gujarati)"),
        NumberItem(7, "7", "७", "Seven", "सात (Hindi) / સાત (Gujarati)"),
        NumberItem(8, "8", "८", "Eight", "आठ (Hindi) / આઠ (Gujarati)"),
        NumberItem(9, "9", "९", "Nine", "नौ (Hindi) / નવ (Gujarati)"),
        NumberItem(10, "10", "१०", "Ten", "दस (Hindi) / દસ (Gujarati)"),
        NumberItem(11, "11", "११", "Eleven", "ग्यारह (Hindi) / અગિયાર"),
        NumberItem(12, "12", "१२", "Twelve", "बारह (Hindi) / બાર"),
        NumberItem(13, "13", "१३", "Thirteen", "तेरह (Hindi) / તેર"),
        NumberItem(14, "14", "१४", "Fourteen", "चौदह (Hindi) / ચૌદ"),
        NumberItem(15, "15", "१५", "Fifteen", "पंद्रह (Hindi) / પંદર"),
        NumberItem(16, "16", "१६", "Sixteen", "सोलह (Hindi) / સોળ"),
        NumberItem(17, "17", "१७", "Seventeen", "सत्रह (Hindi) / સત્તર"),
        NumberItem(18, "18", "१८", "Eighteen", "अठारह (Hindi) / અઢાર"),
        NumberItem(19, "19", "१९", "Nineteen", "उन्नीस (Hindi) / ઓગણિસ"),
        NumberItem(20, "20", "२०", "Twenty", "बीस (Hindi) / વીસ")
    )

    val animatedVideos = listOf(
        // English
        AnimatedVideoItem(
            "Twinkle Twinkle Little Star",
            "An enchanting, animated trip through the night sky with beautiful stars.",
            "yCjJyiqpAuU",
            "English",
            "⭐"
        ),
        AnimatedVideoItem(
            "The Wheels on the Bus",
            "Sing along with the colorful chugging school bus around this cartoon town!",
            "e_049yYKzYc",
            "English",
            "🚌"
        ),
        AnimatedVideoItem(
            "Old MacDonald Had a Farm",
            "Meet the animated cow, horse, pig, and ducks making funny sounds!",
            "V_I_MhJIn9g",
            "English",
            "🐄"
        ),
        
        // Hindi
        AnimatedVideoItem(
            "Lakdi Ki Kathi",
            "Famous animated kids rhyme about a wooden horse riding in the street.",
            "V0-V0U4uTjI",
            "Hindi",
            "🐴"
        ),
        AnimatedVideoItem(
            "Machhli Jal Ki Rani Hai",
            "Watch a shiny fish swim in clear animated water, jumping cheerfully.",
            "y8v_hctqYt4",
            "Hindi",
            "🐟"
        ),
        AnimatedVideoItem(
            "Chanda Mama Door Ke",
            "Animated tale of the friendly Moon-Uncle cooking sweet pudding on a plate.",
            "9oX_1v1XFMo",
            "Hindi",
            "🌙"
        ),
        
        // Gujarati
        AnimatedVideoItem(
            "Ek Bilo Paatlo",
            "Playful animated poem about a kitten wearing shiny spectacles.",
            "zVq9L88QByA",
            "Gujarati",
            "🐱"
        ),
        AnimatedVideoItem(
            "Varta Re Varta",
            "Classic Gujarati storytelling rhyme with colorful farm cartoons and characters.",
            "Odf3SlypC60",
            "Gujarati",
            "📖"
        ),
        AnimatedVideoItem(
            "Chanda Mama Alik Dhalik",
            "Traditional lullaby animation of the silver crescent moon and stars.",
            "6gUu9M8yY2I",
            "Gujarati",
            "🌌"
        )
    )
}
