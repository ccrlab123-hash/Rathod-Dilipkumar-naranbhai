package com.example

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

class PreschoolTtsManager(private val context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.e("PreschoolTtsManager", "Failed to construct TextToSpeech", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            // Configure listeners
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {}
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {}
            })
            // Default language
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("PreschoolTtsManager", "Default US English is not supported")
            }
        } else {
            Log.e("PreschoolTtsManager", "TextToSpeech initialization failed with status: $status")
        }
    }

    fun speakEnglish(text: String) {
        speak(text, Locale.US)
    }

    fun speakHindi(text: String) {
        speak(text, Locale("hi", "IN"))
    }

    fun speakGujarati(text: String) {
        speak(text, Locale("gu", "IN"))
    }

    private fun speak(text: String, locale: Locale) {
        if (!isInitialized || tts == null) {
            Log.e("PreschoolTtsManager", "TTS not initialized, cannot speak: $text")
            return
        }
        try {
            tts?.setLanguage(locale)
            tts?.setPitch(1.1f) // Slightly higher, child-friendly pitch!
            tts?.setSpeechRate(0.85f) // Slightly slower speech rate so pre-schoolers understand clearly!
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "preschool_speech_id")
        } catch (e: Exception) {
            Log.e("PreschoolTtsManager", "Error in speaking: $text", e)
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: Exception) {
            Log.e("PreschoolTtsManager", "Error stopping/shutting down TTS", e)
        } finally {
            tts = null
            isInitialized = false
        }
    }
}
