package com.example.aula3107

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var contador: TextView
    private lateinit var btIniciar: Button
    private lateinit var btVoltar: Button
    private lateinit var etTempo: EditText

    private var timeInSeconds = 0
    private var isRunning = false
    private var isPaused = false
    private var job: Job? = null
    private var tempoMaximo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contador = findViewById(R.id.txtCount)
        btIniciar = findViewById(R.id.btiniciar)
        btVoltar = findViewById(R.id.btvoltar)
        etTempo = findViewById(R.id.etTempo)

        btIniciar.setOnClickListener {
            if (!isRunning) {
                tempoMaximo = etTempo.text.toString().toIntOrNull() ?: 0
                startCounter()
            } else if (isPaused) {
                resumeCounter()
            } else {
                pauseCounter()
            }
        }

        btVoltar.setOnClickListener {
            if (isRunning) {
                stopCounter()
            } else {
                finish()
            }
        }
    }

    private fun startCounter() {
        isRunning = true
        isPaused = false
        btIniciar.text = "Pausar"
        btVoltar.text = "Parar"

        job = CoroutineScope(Dispatchers.Main).launch {
            while (timeInSeconds < tempoMaximo && isRunning && !isPaused) {
                updateTimerText()
                delay(1000)
                timeInSeconds += 1
            }

            if (timeInSeconds >= tempoMaximo) {
                showAlarmScreen()
            }
        }
    }

    private fun pauseCounter() {
        isPaused = true
        btIniciar.text = "Continuar"
        btVoltar.text = "Parar"
    }

    private fun resumeCounter() {
        isPaused = false
        btIniciar.text = "Pausar"
        startCounter()
    }

    private fun stopCounter() {
        isRunning = false
        isPaused = false
        timeInSeconds = 0
        btIniciar.text = "Iniciar"
        btVoltar.text = "Voltar"
        contador.text = "00:00"
        job?.cancel()
    }

    private fun showAlarmScreen() {
        stopCounter()
        val intent = Intent(this, AlarmActivity::class.java)
        startActivity(intent)
    }

    private fun updateTimerText() {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        contador.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
