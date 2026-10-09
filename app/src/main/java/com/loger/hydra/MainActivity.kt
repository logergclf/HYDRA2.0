package com.loger.hydra

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var tvChatHistory: TextView
    private lateinit var etInput: EditText
    private lateinit var btnSend: Button
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvChatHistory = findViewById(R.id.tvChatHistory)
        etInput = findViewById(R.id.etInput)
        btnSend = findViewById(R.id.btnSend)
        scrollView = findViewById(R.id.scrollView)

        btnSend.setOnClickListener {
            val userText = etInput.text.toString().trim()
            if (userText.isNotEmpty()) {
                sendMessageToHydra(userText)
            }
        }
    }

    private fun sendMessageToHydra(message: String) {
        appendMessage("Tú: $message\n")
        etInput.text.clear()

        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.sendMessage(ChatRequest(message = message))
                val modelInfo = response.modelUsed?.let { " [$it]" } ?: ""
                appendMessage("HYDRA$modelInfo: ${response.response}\n\n")
            } catch (e: Exception) {
                appendMessage("HYDRA: Error de conexión (${e.localizedMessage})\n\n")
            }
        }
    }

    private fun appendMessage(text: String) {
        tvChatHistory.append(text)
        scrollView.post {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }
}
