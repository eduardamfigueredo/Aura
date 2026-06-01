package com.example.aura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class OnboardingFirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate o layout para este fragmento
        return inflater.inflate(R.layout.fragment_onboarding_first, container, false)
    }

    // Este método roda assim que o layout termina de ser desenhado na tela
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Encontra o botão "Próximo" pelo ID que você colocou no XML
        val btnProximo = view.findViewById<Button>(R.id.btn_proximo)

        // 2. Configura a ação de clique do botão
        btnProximo.setOnClickListener {

            // 3. Faz a troca do primeiro fragmento pelo segundo dentro da MainActivity
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OnboardingSecondFragment()) // Troca de tela
                .addToBackStack(null) // Permite que o usuário volte se apertar o botão voltar do celular
                .commit()
        }
    }
}