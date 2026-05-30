package com.example.aura

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Essa linha conecta a atividade ao arquivo activity_main.xml
        setContentView(R.layout.activity_main)
    }
}