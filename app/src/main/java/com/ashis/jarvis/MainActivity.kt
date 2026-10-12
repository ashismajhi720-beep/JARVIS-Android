package com.ashis.jarvis

import android.app.Activity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.Locale

class MainActivity : Activity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var message: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(this, this)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 64, 32, 32)
        }

        message = TextView(this).apply {
            text = "नमस्ते! मैं JARVIS हूँ।"
            textSize = 24f
        }

        val button = Button(this).apply {
            text = "JARVIS से बात करें"
            setOnClickListener {
                val reply = "नमस्ते! मैं आपका JARVIS असिस्टेंट हूँ।"
                message.text = reply
                if (::tts.isInitialized) {
                    tts.speak(reply, TextToSpeech.QUEUE_FLUSH, null, "jarvis_reply")
                }
            }
        }

        layout.addView(message)
        layout.addView(button)
        setContentView(layout)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("hi", "IN")
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
