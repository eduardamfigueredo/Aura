package com.example.aura

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Carrega a sua Splash Screen (Logo e fundo creme)
        setContentView(R.layout.activity_main)

        val splashLayout = findViewById<LinearLayout>(R.id.layout_splash)

        // 2. Conta 2 segundos da Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({

            // 3. Esconde a Splash Screen
            splashLayout.visibility = View.GONE

            // 4. VERIFICAÇÃO DE LOGIN SALVO:
            // Acessa a mesma mini memória "AuraPrefs" que criamos no LoginFragment
            val sharedPreferences = getSharedPreferences("AuraPrefs", Context.MODE_PRIVATE)
            val isLogged = sharedPreferences.getBoolean("isLogged", false)

            if (isLogged) {
                // Se o login estiver salvo, pula direto para a Home!
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            } else {
                // Se for o primeiro acesso, segue o fluxo lindo do seu Onboarding
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, OnboardingFirstFragment())
                    .commit()
            }

        }, 2000L)
    }
}