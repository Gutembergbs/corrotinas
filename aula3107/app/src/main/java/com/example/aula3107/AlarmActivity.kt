package com.example.aula3107

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val btDismiss = findViewById<Button>(R.id.btDismiss)
        btDismiss.setOnClickListener {
            finish() // Fecha a activity e volta para a anterior
        }

        // Aqui você pode adicionar código para tocar um som ou vibrar o telefone, se desejar.
    }
}
