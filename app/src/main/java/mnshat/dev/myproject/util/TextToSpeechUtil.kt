package mnshat.dev.myproject.util

import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeechService
import android.speech.tts.UtteranceProgressListener
import android.text.Html
import android.text.Spanned

class TextToSpeechUtil ( val  textToSpeech: TextToSpeech) {


    fun debug(){

        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                log("TTS", "Speech started")
            }

            override fun onDone(utteranceId: String?) {
                log("TTS", "Speech done")
            }

            override fun onError(utteranceId: String?) {
                log("TTS", "Speech error for utterance: $utteranceId")
            }
        }
        )

    }

    private fun htmlToText(html: String): String {
        val spanned: Spanned =
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        return spanned.toString()
    }


    fun speakText(html: String){
        log("speakText")
        val plainText = htmlToText(html)
        val parts = plainText.chunked(4000)
        for ((index, part) in parts.withIndex()) {
            log("speakText", "part: $part")
            textToSpeech.speak(part, TextToSpeech.QUEUE_ADD, null, "chunk_$index")
        }
    }


    fun release(){
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}