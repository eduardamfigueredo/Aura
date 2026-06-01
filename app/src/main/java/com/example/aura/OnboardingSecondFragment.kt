package com.example.aura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class OnboardingSecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate do layout da segunda tela
        return inflater.inflate(R.layout.fragment_onboarding_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Encontra o botão pelo ID correto do XML
        val btnProximoDois = view.findViewById<Button>(R.id.btn_proximo_2)

        // 2. Configura o clique para avançar
        btnProximoDois.setOnClickListener {
            // Substitui o fragmento atual pelo terceiro onboarding
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OnboardingThirdFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}