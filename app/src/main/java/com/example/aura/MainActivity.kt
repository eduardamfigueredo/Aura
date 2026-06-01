package com.example.aura

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Carrega a sua Splash Screen em XML normal
        setContentView(R.layout.activity_main)

        val splashLayout = findViewById<LinearLayout>(R.id.layout_splash)

        // 2. Conta 2 segundos bem rapidinho
        Handler(Looper.getMainLooper()).postDelayed({

            // 3. Esconde o fundo creme e a logo da Splash
            splashLayout.visibility = View.GONE

            // 4. Coloca o seu primeiro fragmento de onboarding em XML na tela
            // Como eles estão na mesma pasta raiz, ele reconhece direto sem import!
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OnboardingFirstFragment())
                .commit()

        }, 2000L)
    }
}